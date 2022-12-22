package com.seca.skincare.fragment

import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.*
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.seca.skincare.R
import com.seca.skincare.activity.BaseActivity
import com.seca.skincare.adapter.*
import com.seca.skincare.model.*
import com.seca.skincare.retrofit.APIClient
import com.seca.skincare.retrofit.APIInterface
import com.seca.skincare.retrofit.response.RestResponse
import com.seca.skincare.utils.ErrorResponseHandler
import com.seca.skincare.utils.ImageChosser
import com.seca.skincare.utils.SharedPreference
import com.seca.skincare.utils.Utility
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File


class AIScanFragment: Fragment(), View.OnClickListener{
    private lateinit var scansAdapter: ScansAdapter
    private var newsList : ArrayList<ImagesModel> = ArrayList()
    lateinit var  view1 : View
    lateinit var  listView : RecyclerView
    lateinit var  mRvCamera : RelativeLayout
    lateinit var  mRvGAlery : RelativeLayout
    lateinit var  mTvNoScan : AppCompatTextView
    var permissions: java.util.ArrayList<String> = java.util.ArrayList()
    private var imageChosser: ImageChosser = ImageChosser()
    private lateinit var mSaveBit: File
    private lateinit var body: MultipartBody.Part
    private lateinit var requestFile: RequestBody


    companion object {
        fun newInstance() = AIScanFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        view1 =  inflater.inflate(R.layout.fragment_aiscan, container, false)

        listView = view1.findViewById(R.id.rv)
        mRvCamera = view1.findViewById(R.id.selfie)
        mRvGAlery = view1.findViewById(R.id.gallery)
        mTvNoScan = view1.findViewById(R.id.no_scan_title)

        mRvGAlery.setOnClickListener(this)
        mRvCamera.setOnClickListener(this)

        return view1

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val  data= ProductModel( "Leny Wilkens","Full Body Specialist","image")
//
//        newsList.add(data)
//        newsList.add(data)
//        newsList.add(data)
//        newsList.add(data)
//        newsList.add(data)
        scansAdapter = ScansAdapter(requireContext(),newsList,this)

        val llm = GridLayoutManager(activity,2)
        llm.orientation = LinearLayoutManager.VERTICAL
        listView.layoutManager = llm
        listView.adapter = scansAdapter
    }


    override fun onResume() {
        super.onResume()

    }

    private fun setAdapter() {


        if(scansAdapter == null) {
            scansAdapter = ScansAdapter(requireContext(),newsList,this)

            val llm = GridLayoutManager(activity,2)
            llm.orientation = LinearLayoutManager.VERTICAL
            listView.layoutManager = llm
            listView.adapter = scansAdapter
        }else
            scansAdapter.notifyDataSetChanged()


        if(newsList.size==0){
            mTvNoScan.visibility = View.VISIBLE
            listView.visibility = View.GONE
        }else{
            listView.visibility = View.VISIBLE
            mTvNoScan.visibility = View.GONE
        }
    }
    override fun onClick(p0: View?) {

        when(p0!!.id){
            R.id.selfie->{



              openDialog(true)
            }

           R.id.gallery ->{
               openDialog(false)

           }

        }
    }

    private fun openDialog(forSelfie: Boolean) {
        try {
            val dialog = Dialog(requireContext())
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.cam_instruction_layout)

//  var switch = dialog.findViewById(R.id.grantSwitch) as SwitchCompat

            val close = dialog.findViewById<ImageView>(R.id.close)
            val submit = dialog.findViewById<Button>(R.id.submit)
            if (!forSelfie){
                dialog.findViewById<AppCompatTextView>(R.id.text_view).text = getString(R.string.upload_instruction)
            }
            close.setOnClickListener { dialog.cancel() }
            submit.setOnClickListener {

                dialog.cancel()
                if (forSelfie)
                askCameraPermission()
                else askPermission()

            }
            dialog.show()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    private fun openProgressDialog() {
        try {
            val dialog = Dialog(requireContext())
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
//            val window: Window = dialog.getWindow()!!
//            window.setLayout(LinearLayoutCompat.LayoutParams.MATCH_PARENT, LinearLayoutCompat.LayoutParams.MATCH_PARENT)
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.scan_instruction_layout)

//  var switch = dialog.findViewById(R.id.grantSwitch) as SwitchCompat

            val close = dialog.findViewById<ImageView>(R.id.close)

            close.setOnClickListener { dialog.cancel() }

            dialog.show()


        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }


    private fun askCameraPermission() {
        permissions.add(Manifest.permission.CAMERA)

        (activity as BaseActivity?)!!.setPermissionsToRequest(permissions)
        (activity as BaseActivity?)!!.askPermission(object : BaseActivity.onPermissionResult{
            override fun onPermissionGranted() {
                startActivityForResult(imageChosser.getPickImageChooserIntent_WithoutCam(activity), 200)
            }

            override fun onPermissiondenied() {}
        })
//        permissionsToRequest = findUnAskedPermissions(permissions)
//        //get the permissions we have asked for before but are not granted..
//        //we will store this in a global list to access later.
//
//
//        //get the permissions we have asked for before but are not granted..
//        //we will store this in a global list to access later.
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            if (permissionsToRequest.size > 0) requestPermissions(
//                permissionsToRequest.toTypedArray(),
//                122
//            )
//        }

        //get the permissions we have asked for before but are not granted..
        //we will store this in a global list to access later.


    }

    private fun askPermission() {

        permissions.add(Manifest.permission.CAMERA)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {

            permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE)
            permissions.add(Manifest.permission.ACCESS_MEDIA_LOCATION)

        } else {

            permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE)
            permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)

        }

        (activity as BaseActivity?)!!.setPermissionsToRequest(permissions)
        (activity as BaseActivity?)!!.askPermission(object : BaseActivity.onPermissionResult{
            override fun onPermissionGranted() {
                startActivityForResult(imageChosser.getPickImageChooserIntent1(activity), 200)
            }

            override fun onPermissiondenied() {}
        })
//        permissionsToRequest = findUnAskedPermissions(permissions)
//        //get the permissions we have asked for before but are not granted..
//        //we will store this in a global list to access later.
//
//
//        //get the permissions we have asked for before but are not granted..
//        //we will store this in a global list to access later.
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            if (permissionsToRequest.size > 0) requestPermissions(
//                permissionsToRequest.toTypedArray(),
//                122
//            )
//        }

        //get the permissions we have asked for before but are not granted..
        //we will store this in a global list to access later.


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        var bitmap: Bitmap
        if (resultCode == Activity.RESULT_OK) {
            if (data != null) {
                try {
                    //  picUri = getRealPathFromURI(data.getData());
                    val path1 : String = imageChosser.getRealPathFromURI(data.data, activity)
                    setImageafterCrop(path1)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            } else {
                val path: String = imageChosser.camProfilePic.getPath()
                Log.e("CAMERA_PATH", path)
                setImageafterCrop(path)
            }
        } else {
            val path: String = imageChosser.camProfilePic.getPath()
            Log.e("CAMERA_PATH", path)
            setImageafterCrop(path)
        }
    }

    private fun setImageafterCrop(s: String) {
        try {
            mSaveBit = File(s)
            val filePath: String = mSaveBit.getPath()
            var bitmap = BitmapFactory.decodeFile(filePath)
            val bitmap1: Bitmap = imageChosser.rotaterequiedImage(filePath, bitmap)
            if (bitmap1 != null) {
                bitmap = bitmap1
            }
            if (bitmap != null) {
                requestFile = RequestBody.create("multipart/form-data".toMediaTypeOrNull(),mSaveBit)
                body =
                    MultipartBody.Part.createFormData(
                        "image",
                        mSaveBit.getName(),
                        requestFile
                    )
                Log.e("bodyyyyyyyyyyyyyyy", body.toString())

//                profileImage.setImageBitmap(Bitmap.createScaledBitmap(bitmap, 160, 160, false))
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

//
}