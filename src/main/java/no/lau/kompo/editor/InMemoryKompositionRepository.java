/*
 * Copyright 2012-2015 the original author or authors.
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
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Dave Syer
 */
public class InMemoryKompositionRepository implements KompositionRepository {

	private static AtomicLong counter = new AtomicLong();

	private final ConcurrentMap<Long, Komposition> kompositions = new ConcurrentHashMap<Long, Komposition>();

	public Iterable<Komposition> findAll() {
		return this.kompositions.values();
	}

	public Komposition save(Komposition komposition) {
		Long id = komposition.id;
		if (id == null) {
			id = counter.incrementAndGet();
			komposition.id = id;
		}
		this.kompositions.put(id, komposition);
		return komposition;
	}

	@Override
	public Komposition findKomposition(Long id) {
		return this.kompositions.get(id);
	}

	@Override
	public void deleteKomposition(Long id) {
		this.kompositions.remove(id);
	}

}
