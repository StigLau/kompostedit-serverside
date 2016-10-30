/*
 * Copyright 2012-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package no.lau.kompo.editor;

import no.lau.kompo.editor.model.Komposition;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

/**
 * @author Rob Winch
 * @author Doo-Hwan Kwak
 */
@Controller
@RequestMapping("/")
public class KompositionController {

	private final KompositionRepository kompositionRepository;

	public KompositionController(KompositionRepository kompositionRepository) {
		this.kompositionRepository = kompositionRepository;
	}

	@GetMapping
	public ModelAndView list() {
		Iterable<Komposition> kompositions = this.kompositionRepository.findAll();
		return new ModelAndView("kompositions/list", "kompositions", kompositions);
	}

	@GetMapping("{id}")
	public ModelAndView view(@PathVariable("id") Komposition komposition) {
		return new ModelAndView("kompositions/view", "komposition", komposition);
	}

	@GetMapping(params = "form")
	public String createForm(@ModelAttribute Komposition komposition) {
		return "kompositions/form";
	}

	@PostMapping
	public ModelAndView create(@Valid Komposition komposition, BindingResult result,
			RedirectAttributes redirect) {
		if (result.hasErrors()) {
			return new ModelAndView("kompositions/form", "formErrors", result.getAllErrors());
		}
		komposition = this.kompositionRepository.save(komposition);
		redirect.addFlashAttribute("globalKomposition", "Successfully created a new kompositions");
		return new ModelAndView("redirect:/{kompositions.id}", "kompositions.id", komposition.id);
	}

	@RequestMapping("foo")
	public String foo() {
		throw new RuntimeException("Expected exception in controller");
	}

	@GetMapping(value = "delete/{id}")
	public ModelAndView delete(@PathVariable("id") Long id) {
		this.kompositionRepository.deleteKomposition(id);
		Iterable<Komposition> kompositions = this.kompositionRepository.findAll();
		return new ModelAndView("kompositions/list", "kompositions", kompositions);
	}

	@GetMapping(value = "modify/{id}")
	public ModelAndView modifyForm(@PathVariable("id") Komposition komposition) {
		return new ModelAndView("kompositions/form", "kompositions", komposition);
	}

}
