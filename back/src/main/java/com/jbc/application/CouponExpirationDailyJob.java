package com.jbc.application;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jbc.model.Coupon;
import com.jbc.repo.CouponRepository;
/**
 * {@code Component} that manages the daily job of the system
 * @author Miri Dahabany
 * @author Eden Bachner
 * @author Jonathan Dalal
 */
@Component
public class CouponExpirationDailyJob implements Runnable, DisposableBean {

	/*? attributes ?*/
	@Autowired
	private CouponRepository couponRepository;
	private final ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);

	/* Constructor */
	private CouponExpirationDailyJob() {
		LocalDateTime hourOfThreadDateTime = LocalDateTime.now().withHour(00).withMinute(00).withSecond(00);
		if (LocalDateTime.now().compareTo(hourOfThreadDateTime) > 0) { 
			hourOfThreadDateTime = hourOfThreadDateTime.plusDays(1);
		}
		Duration duration = Duration.between(LocalDateTime.now(), hourOfThreadDateTime);

		scheduledExecutorService.scheduleAtFixedRate(this, duration.getSeconds(), TimeUnit.DAYS.toSeconds(1),TimeUnit.SECONDS);

	}
	/**
	 * Deletes every {@link com.jbc.model.Coupon} {@code Entity} from the system
	 * 
	 * @see repository#couponRepository
	 */
	@Override
	public void run() {
		for (Coupon coupon : couponRepository.findAll()) {
			if (coupon.getEndDate().getTime() < System.currentTimeMillis()) {
				couponRepository.delete(coupon);
			}
		}
	}
	/**
	 * Shut down the <code>scheduledExecutorService</code> and closing the
	 * connections
	 */
	public void stop() throws InterruptedException {
		scheduledExecutorService.shutdown();


	}

	@Override
	public void destroy() throws Exception {
		stop();

	}
}
