package de.kreth.clubinvoice.business;

import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

import org.junit.jupiter.api.Test;

import de.kreth.clubinvoice.data.User;
import de.kreth.clubinvoice.data.UserBank;

class UserBankingConnectionTest extends UserTestBase {

	@Test
	void testBankInsert() {
		insertUsers(2);
		List<User> allUsers = userBusiness.loadAll();
		User u1 = allUsers.get(0);
		User u2 = allUsers.get(1);

		assertNull(u1.getBank());
		UserBank bank = new UserBank();
		bank.setBankName("Sparkasse");
		bank.setBic("SPKHDE2HXXX");
		bank.setIban("DE53250501800099999999");
		u1.setBank(bank);

		session.update(u1);

		bank = new UserBank();
		bank.setBankName("Sparkasse");
		bank.setBic("SPKHDE2HXXX");
		bank.setIban("DE53250501800099999991");
		u2.setBank(bank);

		session.update(u2);

		allUsers = userBusiness.loadAll();
		u1 = allUsers.get(0);
		u2 = allUsers.get(1);
		// assertEquals(0, u1.getBank().getId());
		// assertEquals(1, u2.getBank().getId());
	}

}
