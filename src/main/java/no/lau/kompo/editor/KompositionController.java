package no.lau.kompo.editor;

import no.lau.kompo.editor.model.Komposition;
import no.lau.kompo.editor.model.Segment;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.validation.Valid;

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
/*
	@GetMapping(value = "segment/modify/{id}")
	public ModelAndView modifySegment(@PathVariable("id") Long index) {
		try {
			Segment segment = kompositionRepository.findKomposition(1l).getSegmentByIndex(index);
			return new ModelAndView("segments/form", "segments", segment);

		} catch (Exception e) {
			throw new RuntimeException("Segment with index " + index + " not found");
		}
	}

	@GetMapping(value = "segment/delete/{id}")
	public ModelAndView deleteSegment(@PathVariable("id") Long id) {
		Komposition komposition = kompositionRepository.findKomposition(1l);
		komposition.deleteSegmentWithIndex(id);
		Iterable<Segment> segments = komposition.getSegments();
		return new ModelAndView("segments/list", "segments", segments);
	}


	@GetMapping("segment/{id}")
	public ModelAndView view(@PathVariable("id") Long index) {
		Komposition komp1 = kompositionRepository.findKomposition(1l);
		try {
			Segment segment = komp1.getSegmentByIndex(index);
			return new ModelAndView("segments/view", "segment", segment);
		} catch (Exception ex) {
			throw new RuntimeException("Not found", ex);
		}
	}

	@GetMapping("segments")
	public ModelAndView segmentList() {
		Komposition komposition = this.kompositionRepository.findKomposition(1l);
		return new ModelAndView("segments/list", "segments", komposition.getSegments());
	}

	@GetMapping(params = "segmentForm")
	public String createSegmentForm(@ModelAttribute Segment segment) {
		return "segments/form";
	}
*/


}
