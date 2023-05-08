package server.presentation.live;

import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import io.micronaut.websocket.WebSocketBroadcaster;
import io.micronaut.websocket.WebSocketSession;
import io.micronaut.websocket.annotation.OnClose;
import io.micronaut.websocket.annotation.OnMessage;
import io.micronaut.websocket.annotation.OnOpen;
import io.micronaut.websocket.annotation.ServerWebSocket;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import server.application.services.cabinet.CabinetService;

@ServerWebSocket("/ws/{topic}/{cabinetId}")
@Secured(SecurityRule.IS_ANONYMOUS)
@Slf4j
public class ScheduleWebSocketController {
    private final WebSocketBroadcaster webSocketBroadcaster;
    private final CabinetService cabinetService;

    public ScheduleWebSocketController(WebSocketBroadcaster webSocketBroadcaster, CabinetService cabinetService) {
        this.webSocketBroadcaster = webSocketBroadcaster;
        this.cabinetService = cabinetService;
    }

    @OnOpen
    public Publisher<?> onOpen(String cabinetId, WebSocketSession session) {
        return this.publishMessage(cabinetId);
    }

    @OnMessage
    public Publisher<?> onMessage(String cabinetId, String message) {
        return this.publishMessage(cabinetId);
    }

    @OnClose
    public void onClose(WebSocketSession webSocketSession) {
        webSocketSession.close();
    }

    public Publisher<?> publishMessage(String cabinetId) {
        var cabinet = this.cabinetService.getCabinetById(Integer.parseInt(cabinetId));
        return this.webSocketBroadcaster.broadcast(cabinet);
    }
}
