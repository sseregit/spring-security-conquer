package spring.security.conquer.admin.controller;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import spring.security.conquer.admin.repository.RoleRepository;
import spring.security.conquer.admin.service.ResourcesService;
import spring.security.conquer.admin.service.RoleService;
import spring.security.conquer.domain.dto.ResourcesDto;
import spring.security.conquer.domain.entity.Resources;
import spring.security.conquer.domain.entity.Role;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class ResourcesController {

    private final ResourcesService resourcesService;
    private final RoleRepository roleRepository;
    private final RoleService roleService;

    public ResourcesController(ResourcesService resourcesService, RoleRepository roleRepository, RoleService roleService) {
        this.resourcesService = resourcesService;
        this.roleRepository = roleRepository;
        this.roleService = roleService;
    }

    @GetMapping("/resources")
    String getResources(Model model) {
        List<Resources> resources = resourcesService.getResources();
        model.addAttribute("resources", resources);

        return "admin/resources";
    }

    @PostMapping("/resources")
    String createResources(ResourcesDto resourcesDto) {
        ModelMapper modelMapper = new ModelMapper();
        Role role = roleRepository.findByRoleName(resourcesDto.getRoleName());
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        Resources resources = modelMapper.map(resourcesDto, Resources.class);
        resources.setRoleSet(roles);

        resourcesService.createResources(resources);

        return "redirect:/admin/resources";
    }

    @GetMapping("/resources/register")
    String resourcesRegister(Model model) {

        List<Role> roleList = roleService.getRoles();
        model.addAttribute("roles", roleList);

        List<String> myRoles = new ArrayList<>();
        model.addAttribute("myRoles", myRoles);

        ResourcesDto resourcesDto = new ResourcesDto();
        Set<Role> roleSet = new HashSet<>();

        roleSet.add(new Role());
        resourcesDto.setRoleSet(roleSet);
        model.addAttribute("resources", resourcesDto);

        return "admin/resourcesdetatils";
    }

    @GetMapping("resoucres/{id}")
    String resourceDetails(@PathVariable Long id, Model model, ModelMap modelMap) {

        List<Role> roleList = roleService.getRoles();
        model.addAttribute("roleList", roleList);

        Resources resources = resourcesService.getResources(id);
        List<String> myRoles = resources.getRoleSet().stream().map(role -> role.getRoleName()).toList();
        model.addAttribute("myRoles", myRoles);

        ModelMapper modelMapper = new ModelMapper();
        ResourcesDto resourcesDto = modelMapper.map(resources, ResourcesDto.class);
        model.addAttribute("resources", resourcesDto);

        return "admin/resourcedetails";
    }

    @GetMapping("/resources/delete/{id}")
    String removeResource(@PathVariable Long id) {
        resourcesService.deleteResources(id);

        return "redirect:/admin/resources";
    }

}
