package ru.softmine.imageconverter.ui.activity

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter
import ru.softmine.imageconverter.databinding.ActivityMainBinding
import ru.softmine.imageconverter.mvp.presenter.MainPresenter
import ru.softmine.imageconverter.mvp.view.MainView
import ru.softmine.imageconverter.ui.ProgressDialogFragment

class MainActivity : MvpAppCompatActivity(), MainView,
    ProgressDialogFragment.OnButtonClickListener {

    private lateinit var progressDialogFragment: ProgressDialogFragment

    private var vb: ActivityMainBinding? = null
    private val presenter by moxyPresenter {
        MainPresenter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vb = ActivityMainBinding.inflate(layoutInflater)
        setContentView(vb?.root)

        vb?.buttonConvert?.setOnClickListener { onConvertClicked() }
    }

    override fun onComplete() {
        Toast.makeText(this, "Complete", Toast.LENGTH_LONG).show()
        progressDialogFragment.dismiss()
    }

    override fun onConvertClicked() {
        val sourcePath = "blah.jpg"
        val targetPath = "blah.png"

        progressDialogFragment = ProgressDialogFragment(this)
        progressDialogFragment.show(supportFragmentManager, "conversionDialogTag")

        vb?.imageView?.setImageURI(Uri.parse(sourcePath))

        presenter.convert(sourcePath, targetPath)
    }

    override fun onError() {
        Toast.makeText(this, "Error", Toast.LENGTH_LONG).show()
        progressDialogFragment.dismiss()
    }

    override fun onPositiveClick() {
        presenter.cancel()
        progressDialogFragment.dismiss()
    }
}