package com.lagou.edu.annotation;

@java.lang.annotation.Target({java.lang.annotation.ElementType.TYPE, java.lang.annotation.ElementType.METHOD})
@java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@java.lang.annotation.Inherited
@java.lang.annotation.Documented
public @interface CustomTransactional {
    @org.springframework.core.annotation.AliasFor("transactionManager")
    java.lang.String value() default "";

    @org.springframework.core.annotation.AliasFor("value")
    java.lang.String transactionManager() default "";

    org.springframework.transaction.annotation.Propagation propagation() default org.springframework.transaction.annotation.Propagation.REQUIRED;

    org.springframework.transaction.annotation.Isolation isolation() default org.springframework.transaction.annotation.Isolation.DEFAULT;

    int timeout() default -1;

    boolean readOnly() default false;

    java.lang.Class<? extends java.lang.Throwable>[] rollbackFor() default {};

    java.lang.String[] rollbackForClassName() default {};

    java.lang.Class<? extends java.lang.Throwable>[] noRollbackFor() default {};

    java.lang.String[] noRollbackForClassName() default {};
}
