package com.jbc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import com.jbc.exceptions.AlreadyExistsException;
import com.jbc.exceptions.DoesNotExistByEmailException;
import com.jbc.exceptions.DoesNotExistByIDException;
import com.jbc.exceptions.IsNullException;
import com.jbc.exceptions.MessageException;
import com.jbc.test.Test;
/**
 * {@code class} that runs the {@code testAll} method in the {@link Test}
 * @author Miri Dahabany
 * @author Eden Bachner
 * @author Jonathan Dalal
 */
@SpringBootApplication

public class CouponSystemPartTwoApplication {

	public static void main(String[] args) throws AlreadyExistsException, MessageException, DoesNotExistByIDException, IsNullException, DoesNotExistByEmailException {
		ApplicationContext context = SpringApplication.run(CouponSystemPartTwoApplication.class, args);

		Test tester = context.getBean(Test.class);
		tester.testAll();



	}
}
