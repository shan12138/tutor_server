package com.dataee.tutorserver.utils;

import com.dataee.tutorserver.entity.TeacherLabel;

/**
 * @Author ChenShanShan
 * @CreateDate 2019/6/10 20:57
 */
public class DoubleToTwo {
    public static TeacherLabel convertDoubleToTwo(TeacherLabel teacherLabel) {
        if (teacherLabel == null)
            return null;
        teacherLabel.setOne((double) (Math.round(teacherLabel.getOne() * 100) / 100));
        teacherLabel.setTwo((double) (Math.round(teacherLabel.getTwo() * 100) / 100));
        teacherLabel.setThree((double) (Math.round(teacherLabel.getThree() * 100) / 100));
        teacherLabel.setFour((double) (Math.round(teacherLabel.getFour() * 100) / 100));
        teacherLabel.setFive((double) (Math.round(teacherLabel.getFive() * 100) / 100));
        teacherLabel.setSix((double) (Math.round(teacherLabel.getSix() * 100) / 100));
        teacherLabel.setSeven((double) (Math.round(teacherLabel.getSeven() * 100) / 100));
        teacherLabel.setEight((double) (Math.round(teacherLabel.getEight() * 100) / 100));
        teacherLabel.setNine((double) (Math.round(teacherLabel.getNine() * 100) / 100));
        teacherLabel.setTen((double) (Math.round(teacherLabel.getTen() * 100) / 100));
        return teacherLabel;
    }
}
