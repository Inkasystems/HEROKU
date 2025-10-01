
package com.example.demo.Controller;

import com.example.demo.Entity.Usuario;
import com.example.demo.Service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/atm")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/login")
    public ResponseEntity<Usuario> login(@RequestParam String usuario, @RequestParam String clave) {
        Optional<Usuario> auth = usuarioService.autenticar(usuario, clave);
        return auth.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(401).build());
    }

    @PostMapping("/depositar")
    public ResponseEntity<Usuario> depositar(@RequestParam Long id, @RequestParam Double monto) {
        Optional<Usuario> optUsuario = usuarioService.obtenerPorId(id);
        if (optUsuario.isPresent()) {
            Usuario u = optUsuario.get();
            u.setSaldo(u.getSaldo() + monto);
            return ResponseEntity.ok(usuarioService.guardar(u));
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/retirar")
    public ResponseEntity<Usuario> retirar(@RequestParam Long id, @RequestParam Double monto) {
        Optional<Usuario> optUsuario = usuarioService.obtenerPorId(id);
        if (optUsuario.isPresent()) {
            Usuario u = optUsuario.get();
            if (u.getSaldo() >= monto) {
                u.setSaldo(u.getSaldo() - monto);
                return ResponseEntity.ok(usuarioService.guardar(u));
            }
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/saldo")
    public ResponseEntity<Double> consultarSaldo(@RequestParam Long id) {
        Optional<Usuario> optUsuario = usuarioService.obtenerPorId(id);
        return optUsuario.map(usuario -> ResponseEntity.ok(usuario.getSaldo()))
                         .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
