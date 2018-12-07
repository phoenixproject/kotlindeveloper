package br.com.codelogic.smsfaster

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.telephony.SmsManager
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import pub.devrel.easypermissions.EasyPermissions
import java.lang.Exception
import android.Manifest
import android.content.Intent
import pub.devrel.easypermissions.AppSettingsDialog

class MainActivity : AppCompatActivity(), TextWatcher, EasyPermissions.PermissionCallbacks {

    /*
    * Método (nativo) que contém o algoritmo responsável por apresentar uma
    * mensagem ao usuário caso ainda falta (ou não) aluma permissão a ser
    * concedida, isso depois da volta ao acionamento de AppSettingsDialog.
    * */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?){
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE){
            var toastContentId = R.string.toast_perms_granted

            /*
            * Escolhendo a mensagem correta a ser apresentada na API Toast
            * caso aluma (ou ambas) permissão ainda não tenha sido fornecida.
            * */
            if(!EasyPermissions.hasPermissions(this, Manifest.permission.SEND_SMS)
            && !EasyPermissions.hasPermissions(
                    this, Manifest.permission.READ_PHONE_STATE
                )){
                toastContentId = R.string.toast_perm_sms_not_yet_granted
            }
            else if(!EasyPermissions.hasPermissions(this, Manifest.permission.SEND_SMS)){
                toastContentId = R.string.toast_perm_sms_not_yet_granted
            }
            else if(!EasyPermissions.hasPermissions(
                    this, Manifest.permission.READ_PHONE_STATE
                )){
                toastContentId = R.string.toast_perm_sms_not_yet_granted
            }

            toast(toastContentId)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        // Eviando a resposta do usuário para a API EasyPermissions
        EasyPermissions.onRequestPermissionsResult(
            requestCode, permissions, grantResults, this
        )
    }

    /*
        * Método do permissõa(ões) negada(s). Neste caso passa por todo um algoritmo
        * para saber qual mensagem aprensetar e como apresentá-la de carodo
        * com a permissão, além de verificar se o box "Não perguntar novamente"
        * foi marcado para assim acionar ou não o AppSettingsDialog.
        * */
    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {

        var title = getString(R.string.title_needed_permission)
        val rationale: String
        val toastContendId: Int
        val permissions = mutableListOf<String>()

        /*
        * Obtendo as mensagens de razão e de toast, o título e as permissões
        * */
        if(!EasyPermissions.hasPermissions(this, Manifest.permission.SEND_SMS)
        && !EasyPermissions.hasPermissions(
                this, Manifest.permission.READ_PHONE_STATE
            )){
            title = getString(R.string.title_needed_permissions)
            rationale = getString(R.string.rationale_sms_phone_state_permissions)
            toastContendId = R.string.toast_needed_permissions
            permissions.add(Manifest.permission.SEND_SMS)
            permissions.add(Manifest.permission.READ_PHONE_STATE)
        }
        else if(!EasyPermissions.hasPermissions(this, Manifest.permission.SEND_SMS)){

            rationale = getString(R.string.rationale_needed_phone_permission)
            toastContendId = R.string.toast_needed_sms_permission
            permissions.add(Manifest.permission.SEND_SMS)
        }
        else{
            rationale = getString(R.string.rationale_needed_phone_permission)
            toastContendId = R.string.toast_needed_phone_permission
            permissions.add(Manifest.permission.READ_PHONE_STATE)
        }

        /*
        * É necessário obter, na propriedade permissions, somente as permissões negadas,
        * pois caso contrário a cixa de diálogo de AppSettingsDialog será apresentada
        * mesmo quando o u´sario não tiver marcado a oção "Não perguntar novamente".
        * */
        if(EasyPermissions.somePermissionPermanentlyDenied(this, permissions)){
            // Caso o "Não perguntar novamente" tenha sido marcado.
            AppSettingsDialog
                .Builder(this)
                .setTitle(title)
                .setRationale(rationale)
                .build()
                .show()
        }
        else{
            /*
            * Para qualquer permisão negada, porém onde nenhuma delas tenha
            * sido marcada como "Não perguntar novamente".
            * */
            toast(toastContendId)
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        /*
        * Com as duas permissões concedidas, invoque o método sendSMS() que contém o SmsManager
        * */
        if(EasyPermissions.hasPermissions(this, Manifest.permission.SEND_SMS)
            && EasyPermissions.hasPermissions(
            this, Manifest.permission.READ_PHONE_STATE
        )){
            sendSMS() //Sobrecarga contendo SmsManager
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        et_message.addTextChangedListener(this)
    }

    /* Para que seja possível contar os caracteres do campo de mensagem e então atualizar o contador em tela */
    override fun onTextChanged(
        text: CharSequence,
        start: Int,
        before: Int,
        count: Int) {

        tv_counter.text = String.format("%d/%s", text.length, getString(R.string.max))
    }

    companion object {
        const val SMS_AND_PHONE_STATE_REQUEST_CODE = 2256
    }

    override fun afterTextChanged(p0: Editable?) {}

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

    // Para limpar o campo de mensagem
    fun clearMessage(view: View){
        et_message.text.clear();
    }

    fun sendSMS(view: View){
        EasyPermissions
            .requestPermissions(
                this,
                getString(R.string.rationale_sms_phone_state_permissions),
                SMS_AND_PHONE_STATE_REQUEST_CODE,
                Manifest.permission.SEND_SMS,
                Manifest.permission.READ_PHONE_STATE
            )
    }

    // Listener de clique, método de envio de SMS
    private fun sendSMS(){
        try{
            val number = String.format("+%s%s%s", et_ddi.text, et_ddd.text, et_number.text)
            val message = et_message.text.toString()

            val smsManager = SmsManager.getDefault()
            smsManager.sendTextMessage(number, null, message, null, null)

            toast(R.string.sms_sent_successfully)
        }
        catch ( e: Exception){
            e.printStackTrace()
            toast(R.string.sms_error)
        }
    }

    private fun toast(messageId: Int){
        Toast
            .makeText(this,getString(messageId), Toast.LENGTH_LONG)
            .show()
    }
}
