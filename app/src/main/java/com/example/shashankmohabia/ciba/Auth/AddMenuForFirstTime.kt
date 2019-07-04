package com.example.shashankmohabia.ciba.Auth

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.RadioButton
import com.example.shashankmohabia.ciba.Core.MerchantActivity
import com.example.shashankmohabia.ciba.Core.db
import com.example.shashankmohabia.ciba.R
import com.example.shashankmohabia.ciba.Utils.Constants.currMerchant
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.add_menu.*

class AddMenuForFirstTime : AppCompatActivity() {
    var vegOrNot = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_menu)
        setSupportActionBar(toolbar_add_menu)

        add_btn.setOnClickListener {
            when {
                item_name.text.isNullOrEmpty() -> item_name.error = "This Field Cannot Be Empty"
                item_preptime.text.isNullOrEmpty() -> item_preptime.error = "This Field Cannot Be Empty"
                item_price.text.isNullOrEmpty() -> item_price.error = "This Field Cannot Be Empty"
                else -> addItem(item_name.text.toString(), item_preptime.text.toString(), item_price.text.toString(), vegOrNot)
            }

        }

    }

    fun onRadioButtonClicked(view: View) {
        if (view is RadioButton) {
            val checked = view.isChecked
            when (view.id) {
                R.id.veg -> vegOrNot = true
                R.id.non_veg -> vegOrNot = false
            }
        }

    }

    private fun addItem(name: String, prepTime: String, price: String, vegOrNot: Boolean) {
        val itemdata = HashMap<String, Any>()
        itemdata["name"] = name
        itemdata["preptime"] = prepTime
        itemdata["price"] = price.toInt()
        itemdata["vegOrNot"] = vegOrNot
        itemdata["availableOrNot"] = "available"
        itemdata["id"]=""

        db.collection("MerchantList").document(currMerchant.id.toString())
                .collection("Menu")
                .add(itemdata)
                .addOnSuccessListener {
                    it.update("id",it.id)
                    item_price.setText("")
                    item_preptime.setText("")
                    item_name.setText("")
                    Toasty.success(this, "one item added").show() }
                .addOnFailureListener { Toasty.error(this,"Error Adding Data").show() }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.add_menu_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val intent = Intent(this, MerchantActivity::class.java)
         startActivity(intent)

        finish()
        return super.onOptionsItemSelected(item)
    }
}