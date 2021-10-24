package com.jbc.logger;

import java.sql.Date;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.jbc.util.ActionsType;
import com.jbc.util.ClientType;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * {@code Logger} {@code Entity} that is used for creating logs in the system.
 * 
 * @author Miri Dahabany
 * @author Eden Bachner
 * @author Jonathan Dalal
 */
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Logger implements Comparable<Logger> {

	/* attributes */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private long id;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private ActionsType action;

	private Date date;

	private LocalTime time;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private ClientType entity;

	private long entityId;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private ClientType performer;

	private String performerData;
	private String newData;
	private String oldData;

	/* constructor */
	public Logger(ActionsType action,ClientType entity, long entityId,
			ClientType performer, String performerData, String newData, String oldData) {
		this.action = action;
		this.entity = entity;
		this.entityId = entityId;
		this.performer = performer;
		this.performerData = performerData;
		this.newData = newData;
		this.oldData = oldData;

		ZonedDateTime zonedDateTimeNow = ZonedDateTime.now(ZoneId.of("Asia/Jerusalem"));
		date = Date.valueOf(zonedDateTimeNow.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));  
		time = LocalTime.parse(zonedDateTimeNow.format(DateTimeFormatter.ofPattern("HH:mm:ss")));

	}

	@Override
	public int compareTo(Logger log) {
		if(log.getDate().after(getDate())) {
			return 1;
		}
		if(log.getDate().equals(getDate()) && log.getTime().isAfter(getTime())) {
			return 1;
		}
		return -1;
	}
}