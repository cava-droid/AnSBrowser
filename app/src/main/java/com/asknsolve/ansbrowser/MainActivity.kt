package com.asknsolve.ansbrowser

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import android.webkit.WebViewClient
import com.asknsolve.ansbrowser.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // 웹뷰 기본 설정
        binding.webView.apply {
            // 자바 스크립트 기능 동작 위하여 설정
            settings.javaScriptEnabled = true
            // 이것을 지정하지 않으면 웹뷰에 페이지가 표시되지 않고 자체 웹 브라우저가 동작함
            webViewClient = WebViewClient()
        }
        // loadUrl() method를 사용하여 http://가 포함된 url을 전달하면 웹뷰에 페이지가 로딩됨
        binding.webView.loadUrl("http://www.google.com")

        // 키보드의 검색 버튼 동작 정의
        // EditText의 setOnEditorActionListener는 에디트텍스트가 선택되고 글자가 입력될 때마다 호출됨
        // 인자: 반응한 뷰, 액션ID, 이벤트
        // 여기서는 뷰와 이벤트를 사용하지 않으므로 _로 대치
        binding.urlEditText.setOnEditorActionListener { _, actionId, _ ->
            // actionId 값은 EditorInfo 클래스에 상수로 정의된 값 중에서 검색 버튼에 해당하는 상수와 비교하여
            // 검색 버튼이 눌렸는지 확인
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                // 검색창에 입력한 주소를 웹뷰에 전달하여 로딩
                binding.webView.loadUrl(binding.urlEditText.text.toString())
                true
            } else {
                false
            }
        }

        // 컨텍스트 메뉴 등록
        registerForContextMenu(binding.webView)
    }

    // 뒤로 가기 동작 재정의
    override fun onBackPressed() {
        // 웹뷰가 이전 페이지로 갈 수 있다면
        if(binding.webView.canGoBack()) {
            // 이전 페이지로 이동
            binding.webView.goBack()
        } else {
            // 그렇지 않다면 원래 동작을 수행
           super.onBackPressed()
        }
    }

    // 옵션 메뉴 리소스 지정
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item?.itemId) {
            R.id.action_google, R.id.action_home -> {
                binding.webView.loadUrl("http://www.google.com")
                return true
            }
            R.id.action_naver -> {
                binding.webView.loadUrl("http://www.naver.com")
                return true
            }
            R.id.action_daum -> {
                binding.webView.loadUrl("http://www.daum.net")
                return true
            }
            R.id.action_call -> {
                val intent = Intent(Intent.ACTION_DIAL)
                intent.data = Uri.parse("tel:010-5353-7642")
                if(intent.resolveActivity(packageManager) != null) {
                    startActivity(intent)
                }
                return true
            }
            R.id.action_send_text -> {
                // 문자 보내기
                return true
            }
            R.id.action_email -> {
                // 이메일 보내기
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.context, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when(item?.itemId){
            R.id.action_share -> {
                // 페이지 공유
                return true
            }
            R.id.action_browser -> {
                // 기본 웹 브라우저에서 열기
                return true
            }
        }
        return super.onContextItemSelected(item)
    }
}