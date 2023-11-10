package fit.asta.health.feature.testimonials.list.vm

import fit.asta.health.core.test.BaseTest
import fit.asta.health.data.testimonials.repo.TestimonialRepoImpl
import io.mockk.clearAllMocks
import io.mockk.mockk
import io.mockk.spyk
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach

class TestimonialListViewModelTest : BaseTest() {

    private lateinit var viewModel: TestimonialListViewModel

    private val repo: TestimonialRepoImpl = mockk(relaxed = true)


    @BeforeEach
    override fun beforeEach() {
        super.beforeEach()
        viewModel = spyk(
            TestimonialListViewModel(
                repo
            )
        )
    }

    @AfterEach
    override fun afterEach() {
        super.afterEach()
        clearAllMocks()
    }
}