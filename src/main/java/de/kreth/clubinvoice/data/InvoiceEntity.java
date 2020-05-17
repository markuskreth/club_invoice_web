package de.kreth.clubinvoice.data;

import java.time.LocalDateTime;

public interface InvoiceEntity {

	int getId();

	LocalDateTime getCreatedDate();

	LocalDateTime getChangeDate();
}
