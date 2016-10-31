package no.lau.kompo.editor;

import no.lau.kompo.editor.model.Komposition;
import no.lau.kompo.editor.model.Segment;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.converter.Converter;

@SpringBootApplication
public class KompostApp {

	@Bean
	public KompositionRepository kompositionRepository() {
		InMemoryKompositionRepository komp = new InMemoryKompositionRepository();
		Komposition firstKomp = new Komposition();
		firstKomp.setReference("Johny 1");
		Segment segment1 = new Segment("Segment1", 0, 120);
		segment1.setIndex(1l);
		firstKomp.addSegment(segment1);
		komp.save(firstKomp);
		return komp;
	}

	@Bean
	public SegmentRepository segmentRepository() {
		return new InMemorySegmentRepository();
	}

	@Bean
	public Converter<String, Komposition> kompositionConverter() {
		return new Converter<String, Komposition>() {
			@Override
			public Komposition convert(String id) {
				return kompositionRepository().findKomposition(Long.valueOf(id));
			}
		};
	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(KompostApp.class, args);
	}

}
