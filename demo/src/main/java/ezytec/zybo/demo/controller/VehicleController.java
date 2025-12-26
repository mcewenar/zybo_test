package ezytec.zybo.demo.controller;

import ezytec.zybo.demo.domain.Vehicle;
import ezytec.zybo.demo.dto.VehicleCreateRequest;
import ezytec.zybo.demo.dto.VehicleResponse;
import ezytec.zybo.demo.dto.VehicleUpdateRequest;
import ezytec.zybo.demo.service.VehicleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/vehiculos")
public class VehicleController {

    private final VehicleService service;


    @PostMapping
    public ResponseEntity<VehicleResponse> create(@Valid @RequestBody VehicleCreateRequest req) {
        Vehicle v = service.create(req.plate(), req.userId());
        return ResponseEntity.ok(new VehicleResponse(v.getId(), v.getPlate(), v.getUser().getId()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<VehicleResponse> get(@PathVariable Long id) {
        Vehicle v = service.get(id);
        return ResponseEntity.ok(new VehicleResponse(v.getId(), v.getPlate(), v.getUser().getId()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<VehicleResponse> update(@PathVariable Long id, @Valid @RequestBody VehicleUpdateRequest req) {
        Vehicle v = service.update(id, req.plate(), req.userId());
        return ResponseEntity.ok(new VehicleResponse(v.getId(), v.getPlate(), v.getUser().getId()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}

