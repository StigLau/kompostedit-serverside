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

import no.lau.kompo.editor.model.Segment;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Dave Syer
 */
public class InMemorySegmentRepository implements SegmentRepository {

	private static AtomicLong counter = new AtomicLong();

	private final ConcurrentMap<Long, Segment> segments = new ConcurrentHashMap<Long, Segment>();

	public Iterable<Segment> findAll() {
		return this.segments.values();
	}

	public Segment save(Segment segment) {
		Long id = segment.getIndex();
		if (id == null) {
			id = counter.incrementAndGet();
			segment.setIndex(id);
		}
		this.segments.put(id, segment);
		return segment;
	}

	@Override
	public Segment findSegment(Long id) {
		return this.segments.get(id);
	}

	@Override
	public void deleteSegment(Long id) {
		this.segments.remove(id);
	}

}
