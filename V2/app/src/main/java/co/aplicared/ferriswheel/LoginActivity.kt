package co.aplicared.ferriswheel


import android.app.AlertDialog
import android.graphics.Typeface
import android.os.Bundle
import android.os.Handler
import android.view.Gravity
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.crashlytics.android.Crashlytics
import com.crashlytics.android.answers.Answers
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import io.fabric.sdk.android.Fabric
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk27.coroutines.onClick

class LoginActivity : AppCompatActivity() {

    private lateinit var mFirebaseAnalytics: FirebaseAnalytics
    private lateinit var mFirebaseAuth: FirebaseAuth

    val handler = Handler()
    val runnable = Runnable {

        mEmailText.visibility = View.VISIBLE
        mPasswordText.visibility = View.VISIBLE
        mSignIn.visibility = View.VISIBLE
        mSignUp.visibility = View.VISIBLE
        mPrivacyButton.visibility = View.VISIBLE
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Fabric.with(this, Crashlytics())
        Fabric.with(this, Answers())
        setContentView(R.layout.activity_login)

        val ent = getString(R.string.please_enter)
        val apas = getString(R.string.a_pass)
        val ypas = getString(R.string.your_pass)


        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this)

        mFirebaseAuth = FirebaseAuth.getInstance()

        if (FirebaseAuth.getInstance().currentUser != null) {
            startActivity(intentFor<MainActivity>().newTask().clearTask())
        } else {
            handler.postDelayed(runnable, 2000)
        }

        mSignUp.onClick {
            if (!mEmailText.text.isEmpty()) {
                if (!mPasswordText.text.isEmpty()) {
                    register(mEmailText.text.toString(), mPasswordText.text.toString())
                } else {
                    dialogMessage(getString(R.string.empty_pass))
                }
            } else {
                dialogMessage(getString(R.string.fui_missing_email_address))
            }
        }

        mSignIn.onClick {
            access(mEmailText.text.toString(), mPasswordText.text.toString())
        }

    }

    fun dialogMessage(msg: String) {
        lateinit var dialog: AlertDialog
        dialog = alert {
            customView {
                verticalLayout {
                    padding = dip(16)
                    textView(msg).textSize = 18f
                    textView(getString(android.R.string.ok)) {
                        this.setTextColor(getColor(R.color.colorPrimary))
                        this.textSize = 18f
                        this.setTypeface(Typeface.DEFAULT, Typeface.BOLD)
                    }.lparams {
                        this.topMargin = 16
                        this.width = 150
                        this.gravity = Gravity.END

                    }.onClick {
                        dialog.dismiss()
                    }
                }
            }
        }.show() as AlertDialog
    }

    fun register(email: String, password: String) {
        startActivity(
            intentFor<SetupActivity>(
                "email" to email,
                "password" to password,
                "text" to "co.aplicared.ferriswheel.LoginActivity.register"
            )
        )
    }

    fun access(email: String, password: String) {

    }

}
