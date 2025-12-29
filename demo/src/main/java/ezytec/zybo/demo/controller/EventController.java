package ezytec.zybo.demo.controller;

import ezytec.zybo.demo.domain.OutboxEvent;
import ezytec.zybo.demo.service.EventService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping(ApiPaths.OUTBOX_EVENTS)
public class EventController {

    private final EventService service;

    @PostMapping("/dispatch")
    public ResponseEntity<List<OutboxEvent>> dispatch() {
        return ResponseEntity.ok(service.dispatchPending());
    }
}
