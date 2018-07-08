package de.kreth.clubinvoice.business;

import org.hibernate.SessionFactory;

import de.kreth.clubinvoice.testutils.MockPropertyStore;

public class DatabaseH2Main {

	public static void main(String[] args) throws Exception {

		SessionFactory factory = InvoiceBusinessTest
				.createFileDatabaseSession("./testdatabase");

		System.out.println("Test database created.");
		InvoiceBusinessTest invBusi = new InvoiceBusinessTest();
		invBusi.session = factory.openSession();

		invBusi.createTestUserInDb();

		invBusi.propStore = new MockPropertyStore();
		invBusi.setUp();
		invBusi.testSave();

		System.out.println(
				InvoiceBusinessTest.class.getSimpleName() + " executed");

		UserBankingConnectionTest.createNow();

		UserBankingConnectionTest userTest = new UserBankingConnectionTest();
		userTest.createMemoryDatabase();
		userTest.setUp();
		userTest.testBankInsert();

		System.out.println(
				UserBankingConnectionTest.class.getSimpleName() + "executed");

		invBusi.session.close();
		System.out.println("session closed");
		factory.close();
		System.out.println("Factory closed");
	}

}
