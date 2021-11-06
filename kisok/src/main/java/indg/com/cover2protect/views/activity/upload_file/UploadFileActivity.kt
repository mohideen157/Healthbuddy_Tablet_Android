package indg.com.cover2protect.views.activity.upload_file

import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import indg.com.cover2protect.R
import kotlinx.android.synthetic.main.activity_upload_file.*
import android.provider.MediaStore
import android.content.Intent
import android.net.Uri
import android.app.ProgressDialog
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.graphics.Color
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.annotation.NonNull
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.regions.Region
import com.amazonaws.regions.Regions
import com.amazonaws.services.s3.AmazonS3Client


import android.widget.Toast
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility
import com.bumptech.glide.Glide
import droidninja.filepicker.FilePickerBuilder
import droidninja.filepicker.FilePickerConst
import droidninja.filepicker.models.sort.SortingTypes
import indg.com.cover2protect.BR
import indg.com.cover2protect.base.BaseActivity
import indg.com.cover2protect.base.BaseActivityBinding
import indg.com.cover2protect.databinding.ActivityUploadFileBinding
import indg.com.cover2protect.model.Awsresponse.aws_response
import indg.com.cover2protect.model.MedicalReportResponse
import indg.com.cover2protect.navigator.MedicalReportNavigator
import indg.com.cover2protect.util.AWS_BUCKET
import indg.com.cover2protect.util.RC_FILE_PICKER_PERM
import indg.com.cover2protect.util.dialog.FancyGifDialog
import indg.com.cover2protect.util.dialog.FancyGifDialogListener
import indg.com.cover2protect.viewmodel.medicalreport.MedicalReportViewModel
import pl.aprilapps.easyphotopicker.DefaultCallback
import pl.aprilapps.easyphotopicker.EasyImage
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import java.io.*
import java.lang.Exception
import javax.inject.Inject


class UploadFileActivity : BaseActivityBinding<ActivityUploadFileBinding>(), View.OnClickListener, MedicalReportNavigator, EasyPermissions.PermissionCallbacks {


    override val bindingVariable: Int
        get() = BR._all
    override val layoutId: Int
        get() = R.layout.activity_upload_file


    @Inject
    lateinit var viewmodel: MedicalReportViewModel
    private var binding:ActivityUploadFileBinding?=null
    private var pd: ProgressDialog? = null
    private var PICK_PDF_REQUEST: Int = 1
    private var STORAGE_PERMISSION_CODE: Int = 123
    private var filePath: Uri? = null
    private var s3: AmazonS3Client? = null
    private val TAKE_PICTURE = 1
    private val SELECT_FILE = 2
    private var dialog: Dialog? = null
    private var path: String = ""
    private var heart_rate: String = ""
    private var bp: String = ""
    private var sugar_fasting: String = ""
    private var sugar_non_fasting: String = ""
    private var triglycerides: String = ""
    private var hdl_cholesterol: String = ""
    private var ldl_cholesterol: String = ""
    private var path_file: String = ""
    private var docPaths = ArrayList<String>()
    private var MAX_ATTACHMENT_COUNT = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = viewDataBinding
        title = "Medical Report"
        changeStatusBarColor()
        viewmodel.setNavigator(this)
        pd = ProgressDialog(this)
        dialog = Dialog(this)
        pd!!.setMessage("Uploading")
        setSupportActionBar(toolbar)
        getData()
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_backspace_black_24dp)
        toolbar.setNavigationOnClickListener { arrow -> onBackPressed() }
        requestStoragePermission()
        gallery_btn.setOnClickListener(this)
        camera_btn.setOnClickListener(this)
        submit.setOnClickListener(this)


        tri_et.afterTextChanged {
            if (!it.isNullOrEmpty()) {
                var tri = it.toDouble()
                when {
                    tri < 150 -> {
                        tri_status.setImageResource(R.drawable.greenicon1)
                        tri_et.setTextColor(Color.GREEN)
                    }
                    tri in 150.0..199.0 -> {
                        tri_status.setImageResource(R.drawable.orangeicon1)
                        tri_et.setTextColor(resources.getColor(R.color.fbutton_color_carrot))
                    }
                    tri > 200 -> {
                        tri_status.setImageResource(R.drawable.redicon1)
                        tri_et.setTextColor(Color.RED)
                    }
                }
            }
        }
        hdl_et.afterTextChanged {
            if (!it.isNullOrEmpty()) {
                var hdl = it.toDouble()
                when {
                    hdl > 60 -> {
                        hdl_status.setImageResource(R.drawable.greenicon1)
                        hdl_et.setTextColor(Color.GREEN)
                    }
                    hdl < 40 -> {
                        hdl_status.setImageResource(R.drawable.redicon1)
                        hdl_et.setTextColor(Color.RED)
                    }
                    hdl in 40.0..59.0 -> {
                        hdl_status.setImageResource(R.drawable.orangeicon1)
                        hdl_et.setTextColor(resources.getColor(R.color.fbutton_color_carrot))
                    }

                }
            }
        }
        ldl_et.afterTextChanged {
            if (!it.isNullOrEmpty()) {
                var ldl = it.toDouble()
                when {
                    ldl in 15.0..130.0 -> {
                        ldl_status.setImageResource(R.drawable.greenicon1)
                        ldl_et.setTextColor(Color.GREEN)
                    }
                    ldl in 131.0..159.0 -> {
                        ldl_status.setImageResource(R.drawable.orangeicon1)
                        ldl_et.setTextColor(resources.getColor(R.color.fbutton_color_carrot))
                    }
                    ldl in 10.0..15.0 -> {
                        ldl_status.setImageResource(R.drawable.orangeicon1)
                        ldl_et.setTextColor(resources.getColor(R.color.fbutton_color_carrot))

                    }
                    ldl < 10 -> {
                        ldl_status.setImageResource(R.drawable.redicon1)
                        ldl_et.setTextColor(Color.RED)
                    }
                    ldl > 160 -> {
                        ldl_status.setImageResource(R.drawable.redicon1)
                        ldl_et.setTextColor(Color.RED)
                    }

                }
            }
        }
        glucose_pp_et.afterTextChanged {
            if (!it.isNullOrEmpty()) {
                var snf = it.toDouble()
                if (snf in 70.0..140.0) {
                    snf_status.setImageResource(R.drawable.greenicon1)
                    glucose_pp_et.setTextColor(Color.GREEN)
                } else if (snf in 140.0..199.0) {
                    snf_status.setImageResource(R.drawable.orangeicon1)
                    glucose_pp_et.setTextColor(resources.getColor(R.color.fbutton_color_carrot))
                } else if (snf < 70 || snf > 200) {
                    snf_status.setImageResource(R.drawable.redicon1)
                    glucose_pp_et.setTextColor(Color.RED)
                }

            }
        }
        glucose_et.afterTextChanged {
            if (!it.isNullOrEmpty()) {
                var sf = it.toDouble()
                if (sf in 60.0..100.0) {
                    sf_status.setImageResource(R.drawable.greenicon1)
                    glucose_et.setTextColor(Color.GREEN)
                } else if (sf in 100.0..125.0) {
                    sf_status.setImageResource(R.drawable.orangeicon1)
                    glucose_et.setTextColor(resources.getColor(R.color.fbutton_color_carrot))
                } else if (sf in 50.0..60.0) {
                    sf_status.setImageResource(R.drawable.orangeicon1)
                    glucose_et.setTextColor(resources.getColor(R.color.fbutton_color_carrot))
                } else if (sf < 50) {
                    sf_status.setImageResource(R.drawable.redicon1)
                    glucose_et.setTextColor(Color.RED)
                } else if (sf > 125) {
                    sf_status.setImageResource(R.drawable.redicon1)
                    glucose_et.setTextColor(Color.RED)
                }
            }
        }
        heart_et.afterTextChanged {
            if (!it.isNullOrEmpty()) {
                var hr = it.toDouble()
                if (hr in 60.0..100.0) {
                    hr_status.setImageResource(R.drawable.greenicon1)
                    heart_et.setTextColor(Color.GREEN)
                } else if (hr in 100.0..120.0) {
                    hr_status.setImageResource(R.drawable.orangeicon1)
                    heart_et.setTextColor(resources.getColor(R.color.fbutton_color_carrot))
                } else if (hr in 50.0..60.0) {
                    hr_status.setImageResource(R.drawable.orangeicon1)
                    heart_et.setTextColor(resources.getColor(R.color.fbutton_color_carrot))
                } else if (hr < 50 || hr > 120) {
                    hr_status.setImageResource(R.drawable.redicon1)
                    heart_et.setTextColor(Color.RED)
                }
            }
        }
    }

    private fun getData() {
        viewmodel.GETAWSDetails()
    }

    private fun getReport(name: String) {
        avi.visibility = View.VISIBLE
        file_ll.visibility = View.VISIBLE
        button_ll.visibility = View.GONE
        upload_ll.visibility = View.GONE
        viewmodel.GetMedicalReport(name)
    }

    fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
        this.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(editable: Editable?) {
                afterTextChanged.invoke(editable.toString())
            }
        })
    }

    private fun requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) === PackageManager.PERMISSION_GRANTED)
            return

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {

        }
        ActivityCompat.requestPermissions(this, arrayOf<String>(Manifest.permission.READ_EXTERNAL_STORAGE), STORAGE_PERMISSION_CODE)
    }


    private fun changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = resources.getColor(R.color.colorAccent)
        }
    }

    private fun uploadImageToAWS(selectedImagePath: String?) {
        try {
            Toast.makeText(this, "Please Wait...", Toast.LENGTH_LONG).show()
            if (!selectedImagePath.isNullOrEmpty()) {
                path_file = selectedImagePath
                pd!!.show()
                var transferUtility: TransferUtility = TransferUtility(s3, this)
                var file = File(selectedImagePath)
                var observer: TransferObserver = transferUtility.upload(AWS_BUCKET, file.name, file)
                observer.setTransferListener(object : TransferListener {
                    override fun onProgressChanged(id: Int, bytesCurrent: Long, bytesTotal: Long) {

                    }

                    override fun onStateChanged(id: Int, state: TransferState?) {
                        if (state != null) {
                            pd!!.hide()
                            if (state == TransferState.COMPLETED) {
                                getReport(file.name)
                            }

                        }
                    }

                    override fun onError(id: Int, ex: Exception?) {
                        pd!!.hide()

                    }

                })
            } else {
                Toast.makeText(this, "Path can't be Empty", Toast.LENGTH_LONG).show()
            }
        } catch (ex: Exception) {
        }


    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            FilePickerConst.REQUEST_CODE_DOC -> {
                if (resultCode == Activity.RESULT_OK && data != null) {
                    docPaths = ArrayList<String>()
                    docPaths.addAll(data.getStringArrayListExtra(FilePickerConst.KEY_SELECTED_DOCS))
                    if (!docPaths.isNullOrEmpty()) {
                        uploadImageToAWS(docPaths!![0])
                    }
                }
            }
        }

        EasyImage.handleActivityResult(requestCode, resultCode, data, this, object : DefaultCallback() {
            override fun onImagePicked(imageFile: File, source: EasyImage.ImageSource, type: Int) {
                when (type) {
                    TAKE_PICTURE -> {
                        Glide.with(this@UploadFileActivity)
                                .load(imageFile)
                                .into(image)
                        path = imageFile.path
                        uploadImageToAWS(path)
                    }
                    SELECT_FILE -> {
                        Glide.with(this@UploadFileActivity)
                                .load(imageFile)
                                .into(image)
                        path = imageFile.path
                        uploadImageToAWS(path)

                    }
                }
            }
        })

    }

    override fun onRequestPermissionsResult(requestCode: Int, @NonNull permissions: Array<String>, @NonNull grantResults: IntArray) {

        if (requestCode == STORAGE_PERMISSION_CODE) {

            //If permission is granted
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(this, "Oops you just denied the permission", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun getPathFromContentUri(uri: Uri): String {
        var path = uri.getPath()
        if (uri.toString().startsWith("content://")) {
            val projection = arrayOf(MediaStore.MediaColumns.DATA)
            val cr = applicationContext.contentResolver
            val cursor = cr.query(uri, projection, null, null, null)
            if (cursor != null) {
                try {
                    if (cursor.moveToFirst()) {
                        path = cursor.getString(0)
                    }
                } finally {
                    cursor.close()
                }
            }

        }
        if (path.isNullOrEmpty()) {
            path = ""
        }
        return path
    }


    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.gallery_btn -> Gallery_Fun()
            R.id.camera_btn -> Camera_Fun()
            R.id.submit -> submitData()
        }
    }


    private fun Camera_Fun() {
        if (s3 != null) {
            pickDocClicked()
        } else {
            Toast.makeText(this, "S3 Credential Required", Toast.LENGTH_LONG).show()
        }

    }

    private fun Gallery_Fun() {
        if (s3 != null) {
            showdialog()
        } else {
            Toast.makeText(this, "S3 Credential Required", Toast.LENGTH_LONG).show()
        }
    }

    fun showdialog() {
        FancyGifDialog.Builder(this)
                .setTitle("SELECT IMAGE")
                .setMessage("PICK IMAGE FROM GALLERY OR CAPTURE IMAGE FROM CAMERA")
                .isCancellable(true)
                .OnPositiveClicked(object : FancyGifDialogListener {
                    override fun OnClick() {
                        if (ContextCompat.checkSelfPermission(this@UploadFileActivity, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
                            ActivityCompat.requestPermissions(this@UploadFileActivity, arrayOf(Manifest.permission.CAMERA), TAKE_PICTURE)

                        } else {
                            EasyImage.openCamera(this@UploadFileActivity, TAKE_PICTURE)

                        }
                    }
                })
                .OnNegativeClicked(object : FancyGifDialogListener {
                    override fun OnClick() {
                        EasyImage.openChooserWithGallery(this@UploadFileActivity, "", SELECT_FILE)
                        dialog!!.dismiss()
                    }
                }).build()
    }

    override fun OnUpdate(msg: Any) {
        avi.visibility = View.GONE
        if (msg is aws_response) {
            var data = msg as aws_response
            var credential: BasicAWSCredentials = BasicAWSCredentials(data.data.Aws_key, data.data.Aws_Secret)
            s3 = AmazonS3Client(credential)
            s3!!.setRegion(Region.getRegion(Regions.US_EAST_1))

        } else {
            Toast.makeText(this, "Data Saved", Toast.LENGTH_LONG).show()

        }
    }


    override fun OnError(message: String) {
        avi.visibility = View.GONE
        Toast.makeText(this@UploadFileActivity, message, Toast.LENGTH_SHORT).show()
    }

    private fun submitData() {
        viewmodel.PostMedicalReport(path_file, bp, heart_rate, sugar_fasting, sugar_non_fasting, triglycerides, hdl_cholesterol, ldl_cholesterol)
    }

    override fun OnSuccess(data: MedicalReportResponse) {
        avi.visibility = View.GONE
        if (data != null) {
            if (!data.BP.isNullOrEmpty()) {
                bp_et.setText(data.BP[0])
                if (data.BP[1].equals("Green")) {
                    bp_status.setImageResource(R.drawable.greenicon1)
                    bp_et.setTextColor(Color.GREEN)
                } else if (data.BP[1].equals("Red")) {
                    bp_status.setImageResource(R.drawable.redicon1)
                    bp_et.setTextColor(Color.RED)
                } else {
                    bp_status.setImageResource(R.drawable.orangeicon1)
                    bp_et.setTextColor(resources.getColor(R.color.fbutton_color_carrot))
                }
                bp = data.BP[0]
            }
            if (!data.Heart_rate.isNullOrEmpty()) {
                heart_et.setText(data.Heart_rate[0])
                heart_rate = data.Heart_rate[0]
                if (data.Heart_rate[1].equals("Green")) {
                    hr_status.setImageResource(R.drawable.greenicon1)
                    heart_et.setTextColor(Color.GREEN)
                } else if (data.Heart_rate[1].equals("Red")) {
                    hr_status.setImageResource(R.drawable.redicon1)
                    heart_et.setTextColor(Color.RED)
                } else {
                    hr_status.setImageResource(R.drawable.orangeicon1)
                    heart_et.setTextColor(resources.getColor(R.color.fbutton_color_carrot))
                }
            }
            if (!data.GLUCOSE_F.isNullOrEmpty()) {
                glucose_et.setText(data.GLUCOSE_F[0])
                sugar_fasting = data.GLUCOSE_F[0]
                if (data.GLUCOSE_F[1].equals("Green")) {
                    sf_status.setImageResource(R.drawable.greenicon1)
                    glucose_et.setTextColor(Color.GREEN)
                } else if (data.GLUCOSE_F[1].equals("Red")) {
                    sf_status.setImageResource(R.drawable.redicon1)
                    glucose_et.setTextColor(Color.RED)
                } else {
                    sf_status.setImageResource(R.drawable.orangeicon1)
                    glucose_et.setTextColor(resources.getColor(R.color.fbutton_color_carrot))

                }

            }
            if (!data.GLUCOSE_PP.isNullOrEmpty()) {
                glucose_pp_et.setText(data.GLUCOSE_PP[0])
                sugar_non_fasting = data.GLUCOSE_PP[0]
                if (data.GLUCOSE_PP[1].equals("Green")) {
                    snf_status.setImageResource(R.drawable.greenicon1)
                    glucose_pp_et.setTextColor(Color.GREEN)
                } else if (data.GLUCOSE_PP[1].equals("Red")) {
                    snf_status.setImageResource(R.drawable.redicon1)
                    glucose_pp_et.setTextColor(Color.RED)
                } else {
                    snf_status.setImageResource(R.drawable.orangeicon1)
                    glucose_pp_et.setTextColor(resources.getColor(R.color.fbutton_color_carrot))
                }
            }

            if (!data.TRIGLYCERIDES.isNullOrEmpty()) {
                tri_et.setText(data.TRIGLYCERIDES[0])
                triglycerides = data.TRIGLYCERIDES[0]
                if (data.TRIGLYCERIDES[1].equals("Green")) {
                    tri_status.setImageResource(R.drawable.greenicon1)
                    tri_et.setTextColor(Color.GREEN)
                } else if (data.TRIGLYCERIDES[1].equals("Red")) {
                    tri_status.setImageResource(R.drawable.redicon1)
                    tri_et.setTextColor(Color.RED)
                } else {
                    tri_status.setImageResource(R.drawable.orangeicon1)
                    tri_et.setTextColor(resources.getColor(R.color.fbutton_color_carrot))
                }
            }

            if (!data.HDL_CHOLESTEROL.isNullOrEmpty()) {
                hdl_et.setText(data.HDL_CHOLESTEROL[0])
                hdl_cholesterol = data.HDL_CHOLESTEROL[0]
                if (data.HDL_CHOLESTEROL[1].equals("Green")) {
                    hdl_status.setImageResource(R.drawable.greenicon1)
                    hdl_et.setTextColor(Color.GREEN)
                } else if (data.HDL_CHOLESTEROL[1].equals("Red")) {
                    hdl_status.setImageResource(R.drawable.redicon1)
                    hdl_et.setTextColor(Color.RED)
                } else {
                    hdl_status.setImageResource(R.drawable.orangeicon1)
                    hdl_et.setTextColor(resources.getColor(R.color.fbutton_color_carrot))
                }
            }

            if (!data.LDL_CHOLESTEROL.isNullOrEmpty()) {
                ldl_et.setText(data.LDL_CHOLESTEROL[0])
                ldl_cholesterol = data.LDL_CHOLESTEROL[0]
                if (data.LDL_CHOLESTEROL[1].equals("Green")) {
                    ldl_status.setImageResource(R.drawable.greenicon1)
                    ldl_et.setTextColor(Color.GREEN)
                } else if (data.LDL_CHOLESTEROL[1].equals("Red")) {
                    ldl_status.setImageResource(R.drawable.redicon1)
                    ldl_et.setTextColor(Color.RED)
                } else {
                    ldl_status.setImageResource(R.drawable.orangeicon1)
                    ldl_et.setTextColor(resources.getColor(R.color.fbutton_color_carrot))
                }

            }
        }
    }

    @AfterPermissionGranted(RC_FILE_PICKER_PERM)
    fun pickDocClicked() {
        if (EasyPermissions.hasPermissions(this, FilePickerConst.PERMISSIONS_FILE_PICKER)) {
            val pdfs = arrayOf(".pdf")
            if (docPaths.size === MAX_ATTACHMENT_COUNT) {
                Toast.makeText(this, "Cannot select more than $MAX_ATTACHMENT_COUNT items",
                        Toast.LENGTH_SHORT).show()
            } else {
                FilePickerBuilder.instance
                        .setMaxCount(1)
                        .setSelectedFiles(docPaths)
                        .setActivityTheme(R.style.FilePickerTheme)
                        .setActivityTitle("Please select doc")
                        .addFileSupport("PDF", pdfs, R.drawable.ic_pdf_box)
                        .enableDocSupport(true)
                        .enableSelectAll(true)
                        .sortDocumentsBy(SortingTypes.name)
                        .withOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                        .pickFile(this)
            }
        } else {
            // Ask for one permission
            EasyPermissions.requestPermissions(this, getString(R.string.rationale_doc_picker),
                    RC_FILE_PICKER_PERM, FilePickerConst.PERMISSIONS_FILE_PICKER)
        }
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).build().show()
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
    }


}
