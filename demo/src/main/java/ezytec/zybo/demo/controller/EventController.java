package ezytec.zybo.demo.controller;

import ezytec.zybo.demo.service.EventService;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
@RequestMapping("/eventos")
public class EventController {

    private final EventService service;
}
