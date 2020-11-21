package solutions.isky.gaurangarevolution.presentation.ui.my_shop

import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.view.MenuItem
import android.view.View
import com.arellomobile.mvp.MvpAppCompatActivity
import kotlinx.android.synthetic.main.activity_no_shop.*
import kotlinx.android.synthetic.main.content_no_shop.*
import solutions.isky.gaurangarevolution.R
import solutions.isky.gaurangarevolution.R.id.*

class NoShopActivity : MvpAppCompatActivity() {
    //is_not_active
    //is_block
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_no_shop)
        setSupportActionBar(toolbar)
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.show()
        btn_create_shop.setOnClickListener {
            startActivity(Intent(this, MyShop::class.java).putExtra("shop_create", true))
            finish()
        }
        val is_on_moderation = intent.getBooleanExtra("is_on_moderation", false);
        val is_not_active = intent.getBooleanExtra("is_not_active", false);
        val is_block = intent.getBooleanExtra("is_block", false);
        val title_text:String?=intent.getStringExtra("title_shop");
        val blocked_reason__text:String?=intent.getStringExtra("blocked_reason");
        view_shop_in_moderation.setVisibility(if (is_on_moderation||is_not_active||is_block) View.VISIBLE else View.GONE)

        view_no_shop.setVisibility(if (is_on_moderation||is_not_active||is_block) View.GONE else View.VISIBLE)
        if (is_on_moderation) {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                text_moderation.setText(Html.fromHtml(String.format(getString(R.string.shop_in_moderation), title_text?:"",Html.FROM_HTML_MODE_COMPACT)))
            }else{
                text_moderation.setText(Html.fromHtml(String.format(getString(R.string.shop_in_moderation), title_text?:"")))
            }

        }else if(is_not_active){
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                text_moderation.setText(Html.fromHtml(String.format(getString(R.string.shop_not_active), title_text?:"",Html.FROM_HTML_MODE_COMPACT)))
            }else{
                text_moderation.setText(Html.fromHtml(String.format(getString(R.string.shop_not_active), title_text?:"")))
            }

        }else if(is_block){
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                text_moderation.setText(Html.fromHtml(String.format(getString(R.string.shop_block), title_text,blocked_reason__text?:"",Html.FROM_HTML_MODE_COMPACT)))
            }else{
                text_moderation.setText(Html.fromHtml(String.format(getString(R.string.shop_block), title_text,blocked_reason__text?:"")))
            }

        }
        btn_return.setOnClickListener { finish() }

    }

    override fun onOptionsItemSelected(menuItem: MenuItem?): Boolean {
        val i = menuItem?.itemId
        if (i == android.R.id.home) {
            onBackPressed()
            return true
        } else {
            return super.onOptionsItemSelected(menuItem)
        }


    }
}