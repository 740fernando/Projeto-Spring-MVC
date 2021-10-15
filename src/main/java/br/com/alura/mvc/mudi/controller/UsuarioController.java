package br.com.alura.mvc.mudi.controller;


import br.com.alura.mvc.mudi.model.Pedido;
import br.com.alura.mvc.mudi.model.StatusPedido;
import br.com.alura.mvc.mudi.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;
import java.util.Locale;

@Controller
@RequestMapping("usuario")
public class UsuarioController {


    @Autowired
    private PedidoRepository pedidoRepository;

    @GetMapping("pedido")//mapemaenta da url
    public String home(Model model, Principal principal){
        List<Pedido> pedidos = pedidoRepository.findAllByUsuario(principal.getName());
        model.addAttribute("pedidos",pedidos);
        return "usuario/home";//mapeamento das pastas
    }


    @GetMapping("pedido/{status}")
    public String porStatus(@PathVariable("status") String status, Model model,Principal principal){
        List<Pedido> pedidos = pedidoRepository.findByStatusEUsuario(StatusPedido.valueOf(status.toUpperCase()),principal.getName()); //transforma essa String em um status pedido
        model.addAttribute("pedidos",pedidos);
        model.addAttribute("status",status);
        return "usuario/home"; //preciso definir o nome da view no retorno do método.
    }

    //Existe uma anotação chamada de “@ExceptionHandler”, onde nós passamos qual é a
    // exceção que queremos mapear. É a “IllegalArgumentException”
    @ExceptionHandler(IllegalArgumentException.class)
    public String onError(){

        return "redirect:/usuario/home";
    }
}



