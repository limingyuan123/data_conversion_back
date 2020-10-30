package nnu.ogms.data_conversion_back.controller;

import nnu.ogms.data_conversion_back.service.RefactorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author mingyuan
 * @Date 2020.10.28 22:37
 */
@RestController
@RequestMapping("/refactor")
public class RefactorController {
    @Autowired
    RefactorService refactorService;
}
