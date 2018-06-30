package de.kreth.clubinvoice.utils;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

import com.vaadin.server.ClientConnector;
import com.vaadin.server.ClientMethodInvocation;
import com.vaadin.server.ErrorHandler;
import com.vaadin.server.Extension;
import com.vaadin.server.ServerRpcManager;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinResponse;
import com.vaadin.shared.Registration;
import com.vaadin.shared.communication.SharedState;
import com.vaadin.ui.UI;
import com.vaadin.ui.renderers.Renderer;

import elemental.json.JsonObject;
import elemental.json.JsonValue;

public class CurrencyRenderer implements Renderer<BigDecimal> {

	private static final long serialVersionUID = 1595475565908329848L;

	@Override
	public Registration addAttachListener(AttachListener listener) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeAttachListener(AttachListener listener) {
		// TODO Auto-generated method stub

	}

	@Override
	public Registration addDetachListener(DetachListener listener) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeDetachListener(DetachListener listener) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<ClientMethodInvocation> retrievePendingRpcCalls() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isConnectorEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Class<? extends SharedState> getStateType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ClientConnector getParent() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void requestRepaint() {
		// TODO Auto-generated method stub

	}

	@Override
	public void markAsDirty() {
		// TODO Auto-generated method stub

	}

	@Override
	public void requestRepaintAll() {
		// TODO Auto-generated method stub

	}

	@Override
	public void markAsDirtyRecursive() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isAttached() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void attach() {
		// TODO Auto-generated method stub

	}

	@Override
	public void detach() {
		// TODO Auto-generated method stub

	}

	@Override
	public Collection<Extension> getExtensions() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeExtension(Extension extension) {
		// TODO Auto-generated method stub

	}

	@Override
	public UI getUI() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void beforeClientResponse(boolean initial) {
		// TODO Auto-generated method stub

	}

	@Override
	public JsonObject encodeState() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean handleConnectorRequest(VaadinRequest request,
			VaadinResponse response, String path) throws IOException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ServerRpcManager<?> getRpcManager(String rpcInterfaceName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ErrorHandler getErrorHandler() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setErrorHandler(ErrorHandler errorHandler) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getConnectorId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class<BigDecimal> getPresentationType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JsonValue encode(BigDecimal value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void remove() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setParent(ClientConnector parent) {
		// TODO Auto-generated method stub

	}

}
