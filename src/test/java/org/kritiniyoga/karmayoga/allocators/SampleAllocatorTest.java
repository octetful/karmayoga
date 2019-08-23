package org.kritiniyoga.karmayoga.allocators;

import org.kritiniyoga.karmayoga.Allocator;
import org.kritiniyoga.karmayoga.AllocatorChecks;

public class SampleAllocatorTest extends AllocatorChecks {
    @Override
    protected Allocator getAllocator() {
        return new SampleAllocator();
    }
}
