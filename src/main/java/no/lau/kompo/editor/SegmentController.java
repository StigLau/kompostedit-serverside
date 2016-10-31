package no.lau.kompo.editor;

import no.lau.kompo.editor.model.Komposition;
import no.lau.kompo.editor.model.Segment;
import no.lau.kompo.editor.model.Segment;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/segments")
public class SegmentController {

	private final SegmentRepository segmentRepository;
	private KompositionRepository kompositionRepository;

	public SegmentController(SegmentRepository segmentRepository, KompositionRepository kompositionRepository) {
		this.segmentRepository = segmentRepository;
		this.kompositionRepository = kompositionRepository;
	}

	@GetMapping()
	public ModelAndView segmentList() {
		Komposition komposition = this.kompositionRepository.findKomposition(1l);
		return new ModelAndView("segments/list", "segments", komposition.getSegments());
	}

	@GetMapping("{id}")
	public ModelAndView view(@PathVariable("id") Long index) {
		Komposition komp1 = kompositionRepository.findKomposition(1l);
		try {
			Segment segment = komp1.getSegmentByIndex(index);
			return new ModelAndView("segments/view", "segment", segment);
		} catch (Exception ex) {
			throw new RuntimeException("Not found", ex);
		}
	}

	@GetMapping(params = "form")
	public String createSegmentForm(@ModelAttribute Segment segment) {
		return "segments/form";
	}


	@PostMapping
	public ModelAndView create(@Valid Segment segment, BindingResult result,
			RedirectAttributes redirect) {
		if (result.hasErrors()) {
			return new ModelAndView("segments/form", "formErrors", result.getAllErrors());
		}
		segment = this.segmentRepository.save(segment);
		//redirect.addFlashAttribute("globalKomposition", "Successfully created a new kompositions");
		return new ModelAndView("redirect:/{segments.id}", "segments.id", segment.id);
	}

	@GetMapping(value = "delete/{id}")
	public ModelAndView delete(@PathVariable("id") Long id) {
		Komposition komposition = kompositionRepository.findKomposition(1l);
		komposition.deleteSegmentWithIndex(id);
		Iterable<Segment> segments = komposition.getSegments();
		return new ModelAndView("segments/list", "segments", segments);
	}


	@GetMapping(value = "modify/{id}")
	public ModelAndView modifyForm(@PathVariable("id") Segment segment) {
		try {
			return new ModelAndView("segments/form", "segments", segment);
		} catch (Exception e) {
			throw new RuntimeException("Segment with index " + segment.getIndex() + " not found");
		}
	}
}
