package com.kh.mini_project.service;

import com.kh.mini_project.common.TimeUtils;
import com.kh.mini_project.dao.MemberDao;
import com.kh.mini_project.dao.NotificationDao;
import com.kh.mini_project.dao.ScheduleDao;
import com.kh.mini_project.dto.ScheduleDto;
import com.kh.mini_project.dto.request.AuthenticateLoginRequest;
import com.kh.mini_project.vo.NotificationVo;
import com.kh.mini_project.vo.ScheduleVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.LinkedHashSet;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final MemberDao memberDao;
    private final ScheduleDao scheduleDao;
    private final NotificationDao notificationDao;

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
}
