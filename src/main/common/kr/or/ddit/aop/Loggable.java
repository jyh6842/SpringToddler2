package kr.or.ddit.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

	// @Loggable(value="값")
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME) // 런타임이면 스프링 프레임 워크에 힌트를 준다
public @interface Loggable {
	// public String value();
}
