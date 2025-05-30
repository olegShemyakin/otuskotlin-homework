import org.akira.otuskotlin.ads.repo.common.AdRepoInitialized
import org.akira.otuskotlin.ads.repo.inmemory.AdRepoInMemory
import org.akira.otuskotlin.ads.repo.tests.*

class AdRepoInMemoryCreateTest : RepoAdCreateTest() {
    override val repo = AdRepoInitialized(
        AdRepoInMemory(randomUuid = { uuidNew.asString() }),
        initObjects = initObjects
    )
}

class AdRepoInMemoryReadTest : RepoAdReadTest() {
    override val repo = AdRepoInitialized(
        AdRepoInMemory(),
        initObjects = initObjects
    )
}

class AdRepoInMemoryDeleteTest : RepoAdDeleteTest() {
    override val repo = AdRepoInitialized(
        AdRepoInMemory(),
        initObjects = initObjects,
    )
}

class AdRepoInMemorySearchTest : RepoAdSearchTest() {
    override val repo = AdRepoInitialized(
        AdRepoInMemory(),
        initObjects = initObjects,
    )
}

class AdRepoInMemoryUpdateTest : RepoAdUpdateTest() {
    override val repo = AdRepoInitialized(
        AdRepoInMemory(),
        initObjects = initObjects,
    )
}