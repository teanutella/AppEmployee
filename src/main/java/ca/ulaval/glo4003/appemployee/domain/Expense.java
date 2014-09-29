package ca.ulaval.glo4003.appemployee.domain;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import org.joda.time.LocalDate;

@XmlRootElement(name = "Expense")
public class Expense {

	private double amount;
	private LocalDate date;
	private String comment;

	public Expense() {
		//Required for JAXB
	}

	public Expense(double amount, LocalDate date, String comment) {
		this.amount = amount;
		this.date = date;
		this.comment = comment;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	@XmlAttribute(name = "Amount")
	public double getAmount() {
		return amount;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	@XmlAttribute(name = "Date")
	public LocalDate getDate() {
		return date;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@XmlAttribute(name = "Comment")
	public String getComment() {
		return comment;
	}
}