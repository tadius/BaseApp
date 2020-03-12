package com.tadiuzzz.baseapp.feature.main

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.Gravity
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.android.material.snackbar.Snackbar
import com.tadiuzzz.baseapp.App.Companion.appComponent
import com.tadiuzzz.baseapp.R
import com.tadiuzzz.baseapp.databinding.ActivityMainBinding
import com.tadiuzzz.baseapp.di.ViewModelFactory
import com.tadiuzzz.baseapp.feature.navigation.NavCommand
import com.tadiuzzz.baseapp.feature.navigation.Navigator
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    private val PERMISSIONS_REQUEST_CODE = 1337
    private val PERMISSIONS_REQUEST_CODE_REPEAT = 322
    private val nonGrantedPermissions = ArrayList<String>()
    private lateinit var permissionsDialog: AlertDialog

    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var navigator: Navigator

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this

        appComponent.inject(this)

        subscribeOnInfo()

        subscribeOnNavigation()

        binding.viewmodel = viewModel

    }

    /**
     * Подписка на события навигации
     */
    private fun subscribeOnNavigation() {
        val navController: NavController =
            Navigation.findNavController(this, R.id.nav_host_fragment)
        navigator.navigationEvent.observe(this, Observer {
            when (it) {
                is NavCommand.NavExit -> finish()
                is NavCommand.NavBack -> navController.popBackStack()
                is NavCommand.NavTo -> navController.navigate(
                    it.navDirections.actionId,
                    it.navDirections.arguments
                )
            }
        })

    }

    /**
     * Подписка на события и данные вьюмодели
     */
    private fun subscribeOnInfo() {
        viewModel.infoHandler.infoMessageEvent.observe(this, Observer {
            showInfoMessage(it)
        })

        viewModel.infoHandler.errorMessageEvent.observe(this, Observer {
            showErrorMessage(it)
        })
    }

    /**
     * Отображает информационное сообщение
     *
     * @param message текст сообщения
     */
    private fun showInfoMessage(message: String) {
        val infoSnackbar = Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG)
        infoSnackbar.setAction("X") {
            infoSnackbar.dismiss()
        }
        infoSnackbar.show()
    }

    /**
     * Отображает сообщение об ошибке
     *
     * @param message текст сообщения
     */
    private fun showErrorMessage(message: String) {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.error_dialog_title))
            .setMessage(message)
            .setPositiveButton("OK", null)
            .create()
            .show()
    }

    override fun onStart() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            checkPermissions()
        super.onStart()
    }

    /**
     * Метод осуществляет проверку статуса разрешений приложения.
     * В случае отсутствия разрешений наполняет мапу вида Имя_разрешения:Разрешение и делает
     * запрос на их предоставление.
     */
    @RequiresApi(Build.VERSION_CODES.M)
    private fun checkPermissions() {
        val permissions = listOf(
            Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )

        for (permission in permissions) {
            if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED)
                nonGrantedPermissions.add(permission)
        }

        if (nonGrantedPermissions.isNotEmpty())
            requestPermissions(
                nonGrantedPermissions.toTypedArray(),
                PERMISSIONS_REQUEST_CODE
            )

    }

    /**
     * @param permission разрешение
     * @return возвращает название разрешения в удобном виде для представления пользователю
     */
    private fun getNamePermission(permission: String): String {
        return when (permission) {
            Manifest.permission.ACCESS_FINE_LOCATION -> "Текущее местоположение"
            Manifest.permission.ACCESS_COARSE_LOCATION -> "Последнее местоположение"
            Manifest.permission.CAMERA -> "Камера"
            Manifest.permission.INTERNET -> "Интернет"
            Manifest.permission.ACCESS_NETWORK_STATE -> "Статус сети"
            Manifest.permission.READ_PHONE_STATE -> "Телефон"
            Manifest.permission.WRITE_EXTERNAL_STORAGE -> "Редактирование памяти"
            Manifest.permission.READ_EXTERNAL_STORAGE -> "Чтение памяти"
            else -> permission
        }
    }

    /**
     * Отображает диалог с описанием необходимых разрешений.
     * При нажатии кнопки "Предоставить" повторно запрашивает разрешения с другим кодом.
     */
    @RequiresApi(Build.VERSION_CODES.M)
    private fun showPermissionsDialog() {
        if (::permissionsDialog.isInitialized && permissionsDialog.isShowing) return

        val message = StringBuilder("Предоставьте необходимые для работы приложения разрешения!")
        for (permission in nonGrantedPermissions) {
            message.append("\n")
            message.append(getNamePermission(permission))
        }

        permissionsDialog = AlertDialog.Builder(this)
            .setTitle("ВНИМАНИЕ")
            .setMessage(message.toString())
            .setCancelable(false)
            .setPositiveButton("ПРЕДОСТАВИТЬ", null)
            .create()
        permissionsDialog.show()
        permissionsDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
            requestPermissions(
                nonGrantedPermissions.toTypedArray(),
                PERMISSIONS_REQUEST_CODE_REPEAT
            )
        }
    }

    /**
     * Колбэк запроса разрешений.
     * Проверяет, все ли запрошенные разрешения были предоставлены.
     * В случае отсутствия некоторых разрешений, в зависимости от кода запроса,
     * вызывает диалог с описанием требуемых разрешений или вызывает системные настройки приложения.
     *
     * @param requestCode код запроса разрешений
     * @param permissions массив разрешений
     * @param grantResults массив результатов запроса
     */
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == PERMISSIONS_REQUEST_CODE || requestCode == PERMISSIONS_REQUEST_CODE_REPEAT) {

            nonGrantedPermissions.clear()
            for (i in grantResults.indices) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) nonGrantedPermissions.add(
                    permissions[i]
                )
            }

            if (nonGrantedPermissions.isNotEmpty()) {
                when (requestCode) {
                    PERMISSIONS_REQUEST_CODE_REPEAT -> openSettings()
                    PERMISSIONS_REQUEST_CODE -> showPermissionsDialog()
                }
            } else if (::permissionsDialog.isInitialized && permissionsDialog.isShowing) {
                permissionsDialog.dismiss()
            }

        }
    }

    /**
     * Открывает системные настройки приложения для предоставления разрешений
     */
    private fun openSettings() {
        val intent = Intent()
        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        val uri = Uri.fromParts("package", packageName, null)
        intent.data = uri
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)

        val toast = Toast.makeText(
            this,
            "Перейдите в Разрешения и предоставьте недостяющие",
            Toast.LENGTH_LONG
        )
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.show()
    }

}
