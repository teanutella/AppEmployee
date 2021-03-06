package ca.ulaval.glo4003.appemployee.persistence;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Singleton;

import org.springframework.stereotype.Repository;

import ca.ulaval.glo4003.appemployee.domain.expense.Expense;
import ca.ulaval.glo4003.appemployee.domain.repository.ExpenseRepository;

@Repository
@Singleton
public class XMLExpenseRepository implements ExpenseRepository {

	private XMLGenericMarshaller<ExpenseXMLAssembler> serializer;
	private Map<String, Expense> expenses = new HashMap<String, Expense>();
	private static String EXPENSES_FILEPATH = "/expenses.xml";

	public XMLExpenseRepository() throws Exception {
		serializer = new XMLGenericMarshaller<ExpenseXMLAssembler>(
				ExpenseXMLAssembler.class);
		parseXML();
	}

	@Override
	public Expense findByUid(String uId) {
		return expenses.get(uId);
	}

	public XMLExpenseRepository(
			XMLGenericMarshaller<ExpenseXMLAssembler> serializer) {
		this.serializer = serializer;
	}

	@Override
	public void store(Expense expense) throws Exception {
		expenses.put(expense.getUid(), expense);
		saveXML();
	}

	@Override
	public List<Expense> findAllExpensesByUser(String userId) {
		List<Expense> foundExpenses = new ArrayList<Expense>();

		for (Expense expense : expenses.values()) {
			if (expense.getUserEmail().equals(userId)) {
				foundExpenses.add(expense);
			}
		}
		return foundExpenses;
	}

	@Override
	public Collection<Expense> findAll() {
		return expenses.values();
	}

	private void saveXML() throws Exception {
		ExpenseXMLAssembler expenseAssembler = new ExpenseXMLAssembler();
		expenseAssembler.setExpenses(new ArrayList<Expense>(expenses.values()));
		serializer.marshall(expenseAssembler, EXPENSES_FILEPATH);
	}

	private void parseXML() throws Exception {
		List<Expense> deserializedExpenses = serializer.unmarshall(
				EXPENSES_FILEPATH).getExpenses();
		for (Expense expense : deserializedExpenses) {
			expenses.put(expense.getUid(), expense);
		}
	}

}
