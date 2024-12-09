package com.kh.mini_project.service;

import com.kh.mini_project.common.TimeUtils;
import com.kh.mini_project.dao.MemberDao;
import com.kh.mini_project.dao.NotificationDao;
import com.kh.mini_project.dao.ReceivedNotificationDao;
import com.kh.mini_project.dao.ScheduleDao;
import com.kh.mini_project.dto.ScheduleDto;
import com.kh.mini_project.dto.request.AuthenticateLoginRequest;
import com.kh.mini_project.vo.NotificationVo;
import com.kh.mini_project.vo.ScheduleVo;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.LinkedHashSet;
import java.util.List;

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

        LinkedHashSet<ScheduleDto.NotificationDto> notificationDtos = scheduleDto.getNotifications();
        if (notificationDtos == null || notificationDtos.isEmpty()) return;
        for (var notification: notificationDtos) {
            NotificationVo notificationVo = new NotificationVo();
            notificationVo.setScheduleNum(scheduleNum);
            notificationVo.setAlertTime(TimeUtils.convertToLocalDateTime(notification.getAlertTime()));
            notificationVo.setAlertMethod(notification.getAlertMethod());
            notificationDao.insert(notificationVo);
        }
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
        LinkedHashSet<ScheduleDto.NotificationDto> updatedNotificationDtos = updatedScheduleDto.getNotifications();
        if (updatedNotificationDtos == null || updatedNotificationDtos.isEmpty()) return;
        for (var notification: updatedNotificationDtos) {
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
