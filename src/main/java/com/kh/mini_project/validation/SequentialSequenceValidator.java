package com.kh.mini_project.validation;

import com.kh.mini_project.dto.DiaryDto.CodingDiaryEntryDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.*;

public class SequentialSequenceValidator implements ConstraintValidator<ValidSequentialSequence, List<CodingDiaryEntryDto>> {

    @Override
    public boolean isValid(List<CodingDiaryEntryDto> codingDiaryEntries, ConstraintValidatorContext context) {
        // 비어 있을 경우 pass
        if (codingDiaryEntries == null || codingDiaryEntries.isEmpty()) {
            return true;
        }

        Set<Integer> sequences = new HashSet<>();
        for (CodingDiaryEntryDto entry : codingDiaryEntries) {
            Integer sequence = entry.getSequence();
            if (sequence == null) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("sequence는 null일 수 없습니다.")
                        .addConstraintViolation();
                return false;
            }
            sequences.add(sequence);
        }

        if (sequences.size() != codingDiaryEntries.size()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("sequence는 서로 고유해야 합니다.")
                    .addConstraintViolation();
            return false;
        }

        List<Integer> sortedSequences = new ArrayList<>(sequences);
        Collections.sort(sortedSequences);

        int expectedSequence = 1;
        for (Integer seq : sortedSequences) {
            if (!seq.equals(expectedSequence)) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("sequence는 1부터 시작하여 1씩 증가해야 합니다.")
                        .addConstraintViolation();
                return false;
            }
            expectedSequence++;
        }

        return true;
    }
}
