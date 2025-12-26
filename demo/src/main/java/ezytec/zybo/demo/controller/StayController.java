package ezytec.zybo.demo.controller;


import ezytec.zybo.demo.service.StayService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/estancias")
public class StayController {

    private final StayService service;

}
