package ezytec.zybo.demo.controller;


import ezytec.zybo.demo.domain.Stay;
import ezytec.zybo.demo.dto.StayEntryRequest;
import ezytec.zybo.demo.dto.StayResponse;
import ezytec.zybo.demo.service.StayService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(ApiPaths.STAYS)
public class StayController {

    private final StayService service;

    @PostMapping("/ingreso")
    public ResponseEntity<StayResponse> entry(@Valid @RequestBody StayEntryRequest req) {
        Stay s = service.registerEntry(req.vehicleId());
        return ResponseEntity.ok(toResponse(s));
    }

    @PostMapping("/{id}/salida")
    public ResponseEntity<StayResponse> exit(@PathVariable Long id) {
        Stay s = service.registerExit(id);
        return ResponseEntity.ok(toResponse(s));
    }

    //MAPPER
    private StayResponse toResponse(Stay s) {
        return new StayResponse(
                s.getId(),
                s.getVehicle().getId(),
                s.getEntryTime(),
                s.getExitTime(),
                s.getMinutesTotal(),
                s.getChargedValue(),
                s.getStatus().name()
        );
    }

}
