package com.demo.spring.programacion.service.usuario;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.demo.spring.programacion.dto.usuario.UsuarioCreateDto;
import com.demo.spring.programacion.dto.usuario.UsuarioDto;
import com.demo.spring.programacion.dto.usuario.UsuarioResumenDto;
import com.demo.spring.programacion.mapper.perfil.PerfilMapper;
import com.demo.spring.programacion.mapper.usuario.UsuarioMapper;
import com.demo.spring.programacion.model.PerfilUsuario;
import com.demo.spring.programacion.model.Usuario;
import com.demo.spring.programacion.repository.usuario.UsuarioRepository;
import com.demo.spring.programacion.repository.usuario.specification.UsuarioSpecifications;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private static final Logger log = LoggerFactory.getLogger(UsuarioServiceImpl.class);

    private final UsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public List<UsuarioDto> obtenerTodos(String nombre, String email, String colorFavorito) {

        Specification<Usuario> spec = Specification.unrestricted();

        if(nombre != null && !nombre.isBlank()){
            spec = spec.and(UsuarioSpecifications.nombre( nombre ));
        }
        if(email != null && !email.isBlank()){
            spec = spec.and(UsuarioSpecifications.email( email ));
        }
        if(colorFavorito != null && !colorFavorito.isBlank()){
            spec = spec.and(UsuarioSpecifications.colorFavorito( colorFavorito ));
        }

        List<Usuario> usuarioList = usuarioRepository.findAll(spec);

        return UsuarioMapper.toDtoList( usuarioList );
    }

    @Override
    public Optional<UsuarioDto> obtenerPorId(UUID id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        if (usuario.isPresent()) {
            Usuario usuarioEntity = usuario.get();
            return Optional.of( UsuarioMapper.toDto(usuarioEntity) );
        }
        return Optional.empty();
    }

    @Override
    public UsuarioDto crearUsuario(UsuarioCreateDto usuarioCreateDto) {
        Usuario usuario = UsuarioMapper.toEntity( usuarioCreateDto );
        usuario = usuarioRepository.save( usuario );
        return UsuarioMapper.toDto( usuario );
    }

    @Override
    public UsuarioDto updateUsuario(UUID id, UsuarioCreateDto usuarioCreateDto) {
        //1. Buscar el usuario por id
        Optional<Usuario> usuario = usuarioRepository.findById(id);

        if (usuario.isPresent()) {
            Optional<Usuario> usuarioExist = usuarioRepository.findByEmail( usuarioCreateDto.getEmail() );

            if (usuarioExist.isPresent() && !usuarioExist.get().getId().equals( id )) {
                throw new IllegalArgumentException("Mail no disponible");
            }

            //2. Setear campo a campo para actualizarlo
            Usuario usuarioEntity = usuario.get();
            usuarioEntity.setNombre( usuarioCreateDto.getNombre() );
            usuarioEntity.setEmail( usuarioCreateDto.getEmail() );

            PerfilUsuario perfilUsuario = usuarioEntity.getPerfil();
            //3. Chequear si no tiene perfil crearlo, sino actualizar sus campos
            if(perfilUsuario == null) {
                perfilUsuario = PerfilMapper.toEntity( usuarioCreateDto.getPerfilUsuarioDto() );
                usuarioEntity.setPerfil( perfilUsuario );
            }else{
                perfilUsuario.setBio( usuarioCreateDto.getPerfilUsuarioDto().getBio() );
                perfilUsuario.setColorFavorito( usuarioCreateDto.getPerfilUsuarioDto().getColorFavorito() );
                perfilUsuario.setFraseDelDia( usuarioCreateDto.getPerfilUsuarioDto().getFraseDelDia() );
            }

            //4. Guardarlo (actualizarlo)
            Usuario usuarioActualizado = usuarioRepository.save( usuarioEntity );
            log.info("Usuario actualizado con id {}", usuarioActualizado.getId());

            //5 devolver el UsuarioDTO.
            return UsuarioMapper.toDto( usuarioActualizado );

        }
        return null;
    }

    @Override
    public boolean eliminarUsuario(UUID id) {

        if ( usuarioRepository.existsById(id) ){
            usuarioRepository.deleteById(id);
            return true;
        }

        return false;
    }

    @Override
    public UsuarioResumenDto obtenerResumenUsuario(UUID id) {

        log.info("Construyendo resumen para el usuario {}", id);

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        String nombre = usuario.getNombre();
        String email = usuario.getEmail();
        String colorFavorito = usuario.getPerfil() != null
                ? usuario.getPerfil().getColorFavorito()
                : null;
                
        //conteo en memoria
        Long cantidadEntradas = usuario.getEntradasDiarias().stream().count();

        LocalDate ultimaEntrada = usuario.getEntradasDiarias().stream()
                .map(e -> e.getFecha())
                .max(LocalDate::compareTo)
                .orElse(null);

        return new UsuarioResumenDto(
                nombre,
                email,
                colorFavorito,
                cantidadEntradas,
                ultimaEntrada
        );
    }

}
