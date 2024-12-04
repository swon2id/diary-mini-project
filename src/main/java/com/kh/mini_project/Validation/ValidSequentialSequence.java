package com.kh.mini_project.Validation;

import jakarta.validation.Constraint; // Bean Validation에서 제약 조건을 정의할 때 사용되는 어노테이션
import jakarta.validation.Payload; // 검증 실패 시 전달할 추가적인 정보를 정의할 때 사용

import java.lang.annotation.*; // 자바의 어노테이션 관련 클래스들을 import

@Documented // 이 어노테이션을 사용하는 곳에서 문서화 도구를 통해 내용을 확인할 수 있도록 설정
@Constraint(validatedBy = SequentialSequenceValidator.class) // 이 어노테이션을 처리할 검증기 클래스를 지정
@Target({ ElementType.FIELD, ElementType.TYPE }) // 어노테이션이 적용될 수 있는 위치를 지정 (필드, 타입)
@Retention(RetentionPolicy.RUNTIME) // 어노테이션의 유지 기간을 지정 (런타임까지 유지)
public @interface ValidSequentialSequence { // @interface 키워드를 사용하여 어노테이션을 정의
    /**
     * 검증 실패 시 반환될 기본 메시지입니다.
     * 필요에 따라 어노테이션 적용 시 메시지를 재정의할 수 있습니다.
     */
    String message() default "sequence는 1부터 시작하여 1씩 증가하는 연속된 수열이어야 합니다.";

    /**
     * 검증 그룹을 지정합니다.
     * 기본값은 빈 배열이며, 필요에 따라 그룹을 정의하여 검증 순서를 제어할 수 있습니다.
     */
    Class<?>[] groups() default {};

    /**
     * 검증 실패 시 전달할 추가적인 정보를 정의합니다.
     * 기본적으로 사용되지 않지만, 확장성을 위해 존재합니다.
     */
    Class<? extends Payload>[] payload() default {};
}
