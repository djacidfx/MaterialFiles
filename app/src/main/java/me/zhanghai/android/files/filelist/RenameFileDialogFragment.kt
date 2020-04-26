/*
 * Copyright (c) 2018 Hai Zhang <dreaming.in.code.zh@gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.android.files.filelist

import android.app.Dialog
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import kotlinx.android.parcel.Parcelize
import me.zhanghai.android.files.R
import me.zhanghai.android.files.file.FileItem
import me.zhanghai.android.files.util.FileNameUtils
import me.zhanghai.android.files.util.ParcelableArgs
import me.zhanghai.android.files.util.args
import me.zhanghai.android.files.util.putArgs
import me.zhanghai.android.files.util.show

class RenameFileDialogFragment : FileNameDialogFragment() {
    private val args by args<Args>()

    override val listener: Listener
        get() = super.listener as Listener

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)

        if (savedInstanceState == null) {
            val file = args.file
            val name = file.name
            binding.nameEdit.setText(name)
            val selectionEnd = if (file.attributes.isDirectory) {
                name.length
            } else {
                FileNameUtils.indexOfExtensionSeparator(name).takeIf { it != -1 } ?: name.length
            }
            binding.nameEdit.setSelection(0, selectionEnd)
        }
        return dialog
    }

    @StringRes
    override val titleRes: Int = R.string.rename

    override fun isNameUnchanged(name: String): Boolean = name == args.file.name

    override fun onOk(name: String) {
        listener.renameFile(args.file, name)
    }

    companion object {
        fun show(file: FileItem, fragment: Fragment) {
            CreateArchiveDialogFragment().putArgs(Args(file)).show(fragment)
        }
    }

    @Parcelize
    class Args(val file: FileItem) : ParcelableArgs

    interface Listener : FileNameDialogFragment.Listener {
        fun renameFile(file: FileItem, newName: String)
    }
}