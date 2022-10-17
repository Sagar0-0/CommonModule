package fit.asta.health.old_testimonials.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import fit.asta.health.old_testimonials.viewmodel.TestimonialsViewModel
import kotlinx.android.synthetic.main.testimonials_activity.*
import org.koin.android.ext.android.inject


class TestimonialsActivity : AppCompatActivity() {

    private val viewTestimonials: TestimonialsView by inject()
    private val viewModelTestimonials: TestimonialsViewModel by inject()

    companion object {

        fun launch(context: Context) {

            val intent = Intent(context, TestimonialsActivity::class.java)
            intent.apply {
                context.startActivity(this)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(viewTestimonials.setContentView(this))
        //setSupportActionBar(findViewById(R.id.tlbTestimonials))
        viewModelTestimonials.observeTestimonialLiveData(
            this,
            TestimonialsObserver(viewTestimonials)
        )
        viewModelTestimonials.fetchTestimonials(15, 0)

        tlbTestimonials.setNavigationOnClickListener {
            onBackPressed()
        }

        viewTestimonials.fabClickListener(ClickListenerImpl(viewModelTestimonials))
        viewTestimonials.submitClickListener(ClickListenerImpl(viewModelTestimonials))
    }

    /*override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.testimonials_toolbar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return viewTestimonials.onOptionsItemSelected(
            item,
            { viewModelTestimonials.fetchTestimonial() }) {
            super.onOptionsItemSelected(item)
        }
    }*/
}