package com.example.javaserver.study.model;

import org.springframework.beans.factory.annotation.Value;

public enum FileType {
    TASK_ATTACHMENT {
        @Override
        public String getBucketName() {
            return tasksBucketName;
        }
    },
    WORK_ATTACHMENT {
        @Override
        public String getBucketName() {
            return worksBucketName;
        }
    },
    QUESTION_ATTACHMENT {
        @Override
        public String getBucketName() {
            return questionsBucketName;
        }
    };

    @Value("${bucket.tasks.name}") private static String tasksBucketName;
    @Value("${bucket.works.name}") private static String worksBucketName;
    @Value("${bucket.questions.name}") private static String questionsBucketName;

    public abstract String getBucketName();
}
