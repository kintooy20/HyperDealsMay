package com.example.kent.hyperdeals.Admin

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.util.SparseBooleanArray
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.EditText
import android.widget.TextView
import com.example.kent.hyperdeals.Model.CategoryModel
import com.example.kent.hyperdeals.Model.SubcategoryModelx
import com.example.kent.hyperdeals.MyAdapters.PromoListAdapter
import com.example.kent.hyperdeals.R
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_add_category_input.*
import kotlinx.android.synthetic.main.activity_add_category_input.view.*
import kotlinx.android.synthetic.main.admin_add_incentives_inputs.*
import kotlinx.android.synthetic.main.app_bar_main3.*
import kotlinx.android.synthetic.main.dialogboxaddsubcategory.view.*
import kotlinx.android.synthetic.main.fragmentpromaplist.*
import org.jetbrains.anko.toast
import java.util.*
import kotlin.collections.ArrayList

class AddCategoryInput : AppCompatActivity() {


    var subcategoryarraylist = ArrayList<SubcategoryModelx>()

    private var mAdapter: AdminAddCategoryAdapter? = null
    private var mSelected: SparseBooleanArray = SparseBooleanArray()
    private var subcategorylist = ArrayList<SubcategoryModelx>()
    private var myDialog: Dialog? = null
    val PICK_IMAGE_REQUEST = 11
    private var mStorage: FirebaseStorage? = null
    private var mStorageReference: StorageReference? = null
    private var mFirebaseFirestore = FirebaseFirestore.getInstance()
    var imageUri: Uri? = null
    private var mImageLink: UploadTask.TaskSnapshot? = null

    private val CategoriesCollectionReference = mFirebaseFirestore.collection("Categories")
    private var catModel : CategoryModel ?= null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_category_input)


        val layoutManager = LinearLayoutManager(applicationContext)
        add_category_recyclerview.layoutManager = layoutManager
        add_category_recyclerview.itemAnimator = DefaultItemAnimator()

        val categorylist = ArrayList<SubcategoryModelx>()


        add_subcategory.setOnClickListener {


            val input = subcategoryinput.text.toString()



            subcategoryarraylist.add(SubcategoryModelx(input, false))

            mAdapter = AdminAddCategoryAdapter(mSelected, subcategoryarraylist)

            add_category_recyclerview.adapter = mAdapter

        }


        adminpickimage.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST)
        }

        adminsetimage.setOnClickListener {
            uploadFile()
        }

        btn_publishcategories.setOnClickListener {
           storeCategoriesToFirestore()
        }


    }


        private fun showDialog() {
            val mDialog = LayoutInflater.from(this).inflate(R.layout.dialogboxaddsubcategory, null)
            val mBuilder = AlertDialog.Builder(this)
                    .setView(mDialog)
                    .setTitle("Add Subcategory")
            val mAlertDialog = mBuilder.show()


/*
            mDialog?.dialog_add?.setOnClickListener {

                val subcategorylist = ArrayList<SubcategoryModelx>()

                val input = subcategoryinput.text.toString()


                subcategoryarraylist.add(SubcategoryModelx(input, false))


                mAdapter = AdminAddCategoryAdapter(mSelected, subcategoryarraylist)


                add_category_recyclerview.adapter = mAdapter

                mAlertDialog.dismiss()
            }*/
        }


        private fun downloadImageLink() {
            mStorageReference!!.child("CategoryPhotos/").downloadUrl.addOnSuccessListener {


                mImageLink!!.downloadUrl

                invisibleimagelink2.setText(mImageLink.toString())

                toast("Nice")

            }
        }


        fun storeCategoriesToFirestore(/*message:String , channelID : String*/) {


            val categoryname = adminentercategoryname.text.toString()

            /*------------------------e store sa ang image--------------------------------------------------*/

            val imagelink = downloadImageLink().toString()



           val imageSaCategory = catModel!!.CategoryImage


            /*------------------------e store sa ang image--------------------------------------------------*/

            CategoriesCollectionReference.document("").set(CategoryModel(categoryname,false,subcategoryarraylist))
                    .continueWith{
                        CategoriesCollectionReference.document("").set(SubcategoryModelx("",false))
                    }


            val intent = Intent(this, Admin::class.java)
            startActivity(intent)


        }

        fun uploadFile() {

            mStorage = FirebaseStorage.getInstance()
            mStorageReference = mStorage!!.reference

            if (imageUri != null) {
                val ref = mStorageReference!!.child("CategoryPhotos/" + UUID.randomUUID().toString())
                ref.putFile(imageUri!!)
                        .addOnSuccessListener {

                            val image = it.downloadUrl.toString()

                            invisibleimagelink2.setText(image)

                            toast("Image Uploaded Successfully")


                        }
                        .addOnFailureListener {
                            toast("Uploading Failed")
                        }
            }

        }

        override fun onActivityResult(requestCode: Int, resultCode: Int, dataa: Intent?) {
            super.onActivityResult(requestCode, resultCode, dataa)

            if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && dataa != null) {
                imageUri = dataa.data

                Picasso.get().load(imageUri).into(adminpickimage)


            }
        }

    }






      /*      val mDialog = LayoutInflater.from(this).inflate(R.layout.dialogboxaddsubcategory,null)
            val mBuilder = AlertDialog.Builder(this)
                    .setView(mDialog)
                    .setTitle("Add Subcategory")
            val  mAlertDialog = mBuilder.show()
*/

           /*     mDialog?.dialog_add?.setOnClickListener{

                   *//* val subcategorylist = ArrayList<SubcategoryModelx>()*//*

                    val input = subcategoryinput.text.toString()



                    mDialog.subcategory1.text = input


                   *//*mAdapter = AdminAddCategoryAdapter(mSelected,subcategorylist)
                    add_category_recyclerview.adapter = mAdapter*//*

                    mAlertDialog.dismiss()

                }*/














