package com.kh.mini_project.service;

import com.kh.mini_project.common.TimeUtils;
import com.kh.mini_project.dao.MemberDao;
import com.kh.mini_project.dao.NotificationDao;
import com.kh.mini_project.dao.ReceivedNotificationDao;
import com.kh.mini_project.dao.ScheduleDao;
import com.kh.mini_project.dto.MonthlyDateDto;
import com.kh.mini_project.dto.ScheduleDto;
import com.kh.mini_project.dto.request.AuthenticateLoginRequest;
import com.kh.mini_project.vo.NotificationVo;
import com.kh.mini_project.vo.ScheduleVo;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final MemberDao memberDao;
    private final ScheduleDao scheduleDao;
    private final NotificationDao notificationDao;
    private final ReceivedNotificationDao receivedNotificationDao;

    @Transactional
    public void saveNewSchedule(AuthenticateLoginRequest loginDto, ScheduleDto scheduleDto) {
        int memberNum = getMemberNumOrThrow(loginDto);

        ScheduleVo scheduleVo = new ScheduleVo();
        scheduleVo.setMemberNum(memberNum);
        scheduleVo.setTitle(scheduleDto.getTitle());
        scheduleVo.setDescription(scheduleDto.getDescription());
        scheduleVo.setStartDate(TimeUtils.convertToLocalDateTime(scheduleDto.getStartDate()));
        scheduleVo.setEndDate(TimeUtils.convertToLocalDateTime(scheduleDto.getEndDate()));
        scheduleVo.setIsAllday(scheduleDto.getIsAllday());
        scheduleVo.setIsImportant(scheduleDto.getIsImportant());

        Integer scheduleNum = scheduleDao.insertAndReturnPk(scheduleVo);
        if (scheduleNum == null) {
            throw new NullPointerException("일정이 생성되었지만, PK를 얻는데 실패하였습니다.");
        }

        List<ScheduleDto.NotificationDto> notificationDtoList = scheduleDto.getNotifications();
        if (notificationDtoList == null || notificationDtoList.isEmpty()) return;
        for (var notificationDto: notificationDtoList) {
            NotificationVo notificationVo = new NotificationVo();
            notificationVo.setScheduleNum(scheduleNum);
            notificationVo.setAlertTime(TimeUtils.convertToLocalDateTime(notificationDto.getAlertTime()));
            notificationVo.setAlertMethod(notificationDto.getAlertMethod());
            notificationDao.insert(notificationVo);
        }
    }

    public List<ScheduleDto> getMonthlyScheduleList(AuthenticateLoginRequest loginDto, MonthlyDateDto dateDto) {
        int memberNum = getMemberNumOrThrow(loginDto);

        String month = dateDto.getMonth();
        if (month.length() == 1 && month.matches("[1-9]")) {
            month = "0" + month;
        }

        // 해당 년월에 해당하는 모든 일정 검색
        List<ScheduleVo> scheduleVoList = scheduleDao.selectByMemberNumAndDate(memberNum, dateDto.getYear(), month);

        // 등록한 일정 없는 경우 null 반환
        if (scheduleVoList.isEmpty()) return null;

        // 각 일정 번호마다 알림 조회
        Map<Integer, List<NotificationVo>> memberNotificationMap = new HashMap<>();
        for (var scheduleVo: scheduleVoList) {
            int scheduleNum = scheduleVo.getScheduleNum();

            // 알림 리스트를 조회하고, SCHEDULE_NUM을 key로 map에 삽입
            List<NotificationVo> notificationVoList = notificationDao.selectByScheduleNum(scheduleNum);
            if (!notificationVoList.isEmpty()) memberNotificationMap.put(scheduleNum, notificationVoList);
        }

        // 최종 반환용 리스트
        List<ScheduleDto> monthlyScheduleList = new ArrayList<>();
        for (var scheduleVo: scheduleVoList) {
            ScheduleDto scheduleDto = new ScheduleDto();
            scheduleDto.setScheduleNum(scheduleVo.getScheduleNum().toString());
            scheduleDto.setTitle(scheduleVo.getTitle());
            scheduleDto.setDescription(scheduleVo.getDescription());
            scheduleDto.setStartDate(TimeUtils.convertLocalDateTimeToString(scheduleVo.getStartDate()));
            scheduleDto.setEndDate(TimeUtils.convertLocalDateTimeToString(scheduleVo.getEndDate()));
            scheduleDto.setIsAllday(scheduleVo.getIsAllday());
            scheduleDto.setIsImportant(scheduleVo.getIsImportant());

            List<NotificationVo> notificationVoListByScheduleNum = memberNotificationMap.get(scheduleVo.getScheduleNum());
            if (notificationVoListByScheduleNum == null || notificationVoListByScheduleNum.isEmpty()) scheduleDto.setNotifications(null);
            else {
                List<ScheduleDto.NotificationDto> notificationDtoList = new ArrayList<>();
                for (var notificationVo: notificationVoListByScheduleNum) {
                    ScheduleDto.NotificationDto notificationDto = new ScheduleDto.NotificationDto();
                    notificationDto.setAlertTime(TimeUtils.convertLocalDateTimeToString(notificationVo.getAlertTime()));
                    notificationDto.setAlertMethod(notificationVo.getAlertMethod());
                    notificationDtoList.add(notificationDto);
                }

                scheduleDto.setNotifications(notificationDtoList);
            }

            monthlyScheduleList.add(scheduleDto);
        }

        return monthlyScheduleList;
    }

    @Transactional
    public void deleteSchedule(AuthenticateLoginRequest loginDto, int scheduleNum) {
        int memberNum = getMemberNumOrThrow(loginDto);
        isScheduleOwner(memberNum, scheduleNum);

        // 알림 및 수신된 알림 삭제
        notificationDao.deleteByScheduleNum(scheduleNum);
        receivedNotificationDao.deleteByScheduleNum(scheduleNum);

        // 일정 삭제
        scheduleDao.deleteByScheduleNum(scheduleNum);
    }

    @Transactional
    public void updateSchedule(AuthenticateLoginRequest loginDto, int scheduleNum, ScheduleDto updatedScheduleDto) {
        int memberNum = getMemberNumOrThrow(loginDto);
        isScheduleOwner(memberNum, scheduleNum);

        if(!scheduleDao.update(
                scheduleNum,
                updatedScheduleDto.getTitle(),
                updatedScheduleDto.getDescription(),
                TimeUtils.convertToLocalDateTime(updatedScheduleDto.getStartDate()),
                TimeUtils.convertToLocalDateTime(updatedScheduleDto.getEndDate()),
                updatedScheduleDto.getIsAllday(),
                updatedScheduleDto.getIsImportant())
        ) {
            throw new EmptyResultDataAccessException("업데이트 된 일정이 존재하지 않습니다. Schedule 번호: " + scheduleNum, 1);
        }

        // 기존 알림 삭제
        notificationDao.deleteByScheduleNum(scheduleNum);

        // 알림 새로 업데이트
        List<ScheduleDto.NotificationDto> updatedNotificationDtoList = updatedScheduleDto.getNotifications();
        if (updatedNotificationDtoList == null || updatedNotificationDtoList.isEmpty()) return;
        for (var notification: updatedNotificationDtoList) {
            NotificationVo notificationVo = new NotificationVo();
            notificationVo.setScheduleNum(scheduleNum);
            notificationVo.setAlertTime(TimeUtils.convertToLocalDateTime(notification.getAlertTime()));
            notificationVo.setAlertMethod(notification.getAlertMethod());
            notificationDao.insert(notificationVo);
        }
    }

    /**
     * 이하는 코드 중복 제거 및 가독성을 높이기 위한 헬퍼 메서드 입니다.
     * */
    private int getMemberNumOrThrow(AuthenticateLoginRequest loginDto) {
        int memberNum = memberDao.selectMemberNumById(loginDto.getId());
        if (memberNum == -1) {
            throw new IllegalArgumentException("ID, PW는 인증되었지만, ID로 memberNum을 조회하는데 실패하였습니다.");
        }
        return memberNum;
    }

    private void isScheduleOwner(int memberNum, int scheduleNum) {
        List<Integer> scheduleNumList = scheduleDao.selectByMemberNum(memberNum);
        boolean exists = scheduleNumList.contains(scheduleNum);
        if (!exists) {
            throw new SecurityException();
        }
    }
}
