package com.futurecode.crackdisplayprank.utils



import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.widget.Toast
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter

/**
 * 15-Year Developer Standard: Pure Static/Object Utility Pattern.
 * ✅ Play Store URL generation, offline QR code generation, clipboard operations,
 * and social platform intent sharing ko ek jagah centralize kar diya hai.
 */
object ScannerQR {

    /**
     * ✅ DYNAMIC URL RESOLVER: App ka package-based Play Store link dynamically fetch karne ke liye.
     * Isko kahin se bhi call karein: ScannerQR.getAppPlayStoreLink(context)
     */
    fun getAppPlayStoreLink(context: Context): String {
        val packageName = context.packageName
        return "https://play.google.com/store/apps/details?id=$packageName"
    }

    /**
     * ✅ REUSABLE QR BITMAP GENERATOR: Kisi bhi dynamic text/URL se high-contrast scanning bitmap banane ke liye.
     * Isko kahin se bhi call karein: ScannerQR.generateQRCode("text_or_url", 512)
     */
    fun generateQRCode(text: String, size: Int = 512): Bitmap? {
        return try {
            val writer = QRCodeWriter()
            // 512x512 matrix resolution keeps scanning smooth on low-end cameras
            val bitMatrix = writer.encode(text, BarcodeFormat.QR_CODE, size, size)
            val width = bitMatrix.width
            val height = bitMatrix.height
            val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)

            for (x in 0 until width) {
                for (y in 0 until height) {
                    // Set black pixels for QR patterns and white for background spacing
                    bitmap.setPixel(x, y, if (bitMatrix.get(x, y)) Color.BLACK else Color.WHITE)
                }
            }
            bitmap
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    /**
     * ✅ SYSTEM SHARE INTENT TRIGGER: Standard android system share sheet launcher.
     */
    fun triggerSystemShareIntent(context: Context, title: String, message: String) {
        try {
            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_SUBJECT, title)
                putExtra(Intent.EXTRA_TEXT, message)
            }
            context.startActivity(Intent.createChooser(shareIntent, "Share App via"))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * ✅ CLIPBOARD COPY HANDLER: Direct string copying to Android system clipboard manager.
     */
    fun copyToClipboard(context: Context, label: String, text: String, successMessage: String = "Copied to clipboard!") {
        try {
            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText(label, text)
            clipboard.setPrimaryClip(clip)
            Toast.makeText(context, successMessage, Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * ✅ SOCIAL SHARER: Target platform validation before direct intents dispatch.
     */
    fun shareToSocialPlatform(
        context: Context,
        packageIdentifier: String,
        baseMessage: String,
        fallbackErrorMessage: String
    ) {
        try {
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                `package` = packageIdentifier
                putExtra(Intent.EXTRA_TEXT, baseMessage)
            }
            context.startActivity(intent)
        } catch (e: Exception) {
            // Target app missing, fallback cleanly to android OS sharing tray chooser
            Toast.makeText(context, fallbackErrorMessage, Toast.LENGTH_SHORT).show()
            triggerSystemShareIntent(context, "Broken Screen Prank", baseMessage)
        }
    }
}