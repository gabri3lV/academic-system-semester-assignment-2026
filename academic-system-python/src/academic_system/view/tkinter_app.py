from __future__ import annotations

import tkinter as tk
from tkinter import messagebox, ttk

from academic_system.controller.authentication_controller import AuthenticationController
from academic_system.controller.controller_factory import ControllerFactory
from academic_system.exception.exceptions import AcademicSystemException, AuthenticationException
from academic_system.security.session import Session


class AcademicSystemTkApp:
    def __init__(self):
        self.root = tk.Tk()
        self.root.title("Academic System")
        self.root.geometry("900x620")
        self.root.minsize(760, 520)

        self.auth_controller = AuthenticationController()
        self.controller = ControllerFactory.get_academic_system_controller()

        self.username = tk.StringVar()
        self.password = tk.StringVar()
        self.class_code = tk.StringVar()
        self.class_title = tk.StringVar()
        self.assessment_class = tk.StringVar()
        self.assessment_type = tk.StringVar(value="exam")
        self.assessment_value = tk.StringVar()
        self.assessment_weight = tk.StringVar()
        self.persistence_type = tk.StringVar(value="TXT")

        self.container = ttk.Frame(self.root, padding=16)
        self.container.pack(fill=tk.BOTH, expand=True)
        self.show_login()

    def run(self):
        self.root.mainloop()

    def clear(self):
        for widget in self.container.winfo_children():
            widget.destroy()

    def show_login(self):
        self.clear()
        self.root.title("Academic System - Login")

        frame = ttk.Frame(self.container, padding=24)
        frame.place(relx=0.5, rely=0.5, anchor=tk.CENTER)

        ttk.Label(frame, text="Academic System", font=("Segoe UI", 18, "bold")).grid(
            row=0, column=0, columnspan=2, pady=(0, 18)
        )
        ttk.Label(frame, text="Username").grid(row=1, column=0, sticky=tk.W, pady=6)
        ttk.Entry(frame, textvariable=self.username, width=28).grid(
            row=1, column=1, pady=6
        )
        ttk.Label(frame, text="Password").grid(row=2, column=0, sticky=tk.W, pady=6)
        password_entry = ttk.Entry(frame, textvariable=self.password, show="*", width=28)
        password_entry.grid(row=2, column=1, pady=6)

        ttk.Button(frame, text="Login", command=self.login).grid(
            row=3, column=0, columnspan=2, sticky=tk.EW, pady=(14, 0)
        )
        password_entry.bind("<Return>", lambda _event: self.login())

    def login(self):
        try:
            user = self.auth_controller.authenticate_and_return(
                self.username.get().strip(),
                self.password.get().strip(),
            )
        except AuthenticationException as exc:
            messagebox.showerror("Login failed", str(exc))
            return

        self.password.set("")
        self.show_main(user.username, user.role.value)

    def show_main(self, username: str, role: str):
        self.clear()
        self.root.title(f"Academic System - {role}")

        header = ttk.Frame(self.container)
        header.pack(fill=tk.X, pady=(0, 12))

        ttk.Label(
            header,
            text=f"Logged in as {username} ({role})",
            font=("Segoe UI", 12, "bold"),
        ).pack(side=tk.LEFT)

        ttk.Button(header, text="Logout", command=self.logout).pack(side=tk.RIGHT)

        notebook = ttk.Notebook(self.container)
        notebook.pack(fill=tk.BOTH, expand=True)

        self.registration_tab = ttk.Frame(notebook, padding=12)
        self.reports_tab = ttk.Frame(notebook, padding=12)

        notebook.add(self.registration_tab, text="Registration")
        notebook.add(self.reports_tab, text="Reports")

        self.build_registration_tab()
        self.build_reports_tab()
        self.refresh_classes()

    def build_registration_tab(self):
        class_box = ttk.LabelFrame(self.registration_tab, text="Class", padding=12)
        class_box.pack(fill=tk.X, pady=(0, 12))

        ttk.Label(class_box, text="Code").grid(row=0, column=0, sticky=tk.W)
        ttk.Entry(class_box, textvariable=self.class_code).grid(
            row=0, column=1, sticky=tk.EW, padx=(8, 0), pady=4
        )
        ttk.Label(class_box, text="Title").grid(row=1, column=0, sticky=tk.W)
        ttk.Entry(class_box, textvariable=self.class_title).grid(
            row=1, column=1, sticky=tk.EW, padx=(8, 0), pady=4
        )
        ttk.Button(class_box, text="Register class", command=self.register_class).grid(
            row=2, column=0, columnspan=2, sticky=tk.EW, pady=(8, 0)
        )
        class_box.columnconfigure(1, weight=1)

        if not Session.is_admin():
            for child in class_box.winfo_children():
                child.configure(state=tk.DISABLED)

        assessment_box = ttk.LabelFrame(
            self.registration_tab, text="Assessment", padding=12
        )
        assessment_box.pack(fill=tk.X, pady=(0, 12))

        ttk.Label(assessment_box, text="Class code").grid(row=0, column=0, sticky=tk.W)
        self.class_combo = ttk.Combobox(
            assessment_box, textvariable=self.assessment_class
        )
        self.class_combo.grid(row=0, column=1, sticky=tk.EW, padx=(8, 0), pady=4)

        ttk.Label(assessment_box, text="Type").grid(row=1, column=0, sticky=tk.W)
        ttk.Combobox(
            assessment_box,
            textvariable=self.assessment_type,
            values=("exam", "assignment", "seminar", "practicalassignment"),
            state="readonly",
        ).grid(row=1, column=1, sticky=tk.EW, padx=(8, 0), pady=4)

        ttk.Label(assessment_box, text="Value").grid(row=2, column=0, sticky=tk.W)
        ttk.Entry(assessment_box, textvariable=self.assessment_value).grid(
            row=2, column=1, sticky=tk.EW, padx=(8, 0), pady=4
        )

        ttk.Label(assessment_box, text="Weight").grid(row=3, column=0, sticky=tk.W)
        ttk.Entry(assessment_box, textvariable=self.assessment_weight).grid(
            row=3, column=1, sticky=tk.EW, padx=(8, 0), pady=4
        )

        ttk.Button(
            assessment_box,
            text="Register assessment",
            command=self.register_assessment,
        ).grid(row=4, column=0, columnspan=2, sticky=tk.EW, pady=(8, 0))
        assessment_box.columnconfigure(1, weight=1)

        persistence_box = ttk.LabelFrame(
            self.registration_tab, text="Persistence", padding=12
        )
        persistence_box.pack(fill=tk.X)

        ttk.Combobox(
            persistence_box,
            textvariable=self.persistence_type,
            values=("TXT", "XML", "JSON"),
            state="readonly",
        ).pack(fill=tk.X, pady=(0, 8))

        ttk.Button(
            persistence_box,
            text="Configure persistence",
            command=self.configure_persistence,
        ).pack(fill=tk.X)

        ttk.Button(persistence_box, text="Save data", command=self.save_data).pack(
            fill=tk.X, pady=(8, 0)
        )

        if not Session.is_admin():
            for child in persistence_box.winfo_children():
                child.configure(state=tk.DISABLED)

    def build_reports_tab(self):
        table_box = ttk.LabelFrame(self.reports_tab, text="Registered classes", padding=12)
        table_box.pack(fill=tk.BOTH, expand=True, pady=(0, 12))

        self.class_table = ttk.Treeview(
            table_box,
            columns=("code", "title", "assessments"),
            show="headings",
            height=8,
        )
        self.class_table.heading("code", text="Code")
        self.class_table.heading("title", text="Title")
        self.class_table.heading("assessments", text="Assessments")
        self.class_table.pack(fill=tk.BOTH, expand=True)

        buttons = ttk.Frame(self.reports_tab)
        buttons.pack(fill=tk.X, pady=(0, 8))

        ttk.Button(
            buttons,
            text="Class summary",
            command=lambda: self.show_report(
                self.controller.generate_class_summary_report()
            ),
        ).pack(side=tk.LEFT, padx=(0, 8))

        ttk.Button(
            buttons,
            text="Weight report",
            command=lambda: self.show_report(
                self.controller.generate_assessment_weight_report()
            ),
        ).pack(side=tk.LEFT, padx=(0, 8))

        ttk.Button(
            buttons,
            text="Persistence report",
            command=lambda: self.show_report(
                self.controller.generate_persistence_configuration_report()
            ),
        ).pack(side=tk.LEFT)

        self.report_text = tk.Text(self.reports_tab, height=12, wrap=tk.WORD)
        self.report_text.pack(fill=tk.BOTH, expand=True)

    def register_class(self):
        try:
            self.controller.register_class(self.class_code.get(), self.class_title.get())
        except Exception as exc:
            messagebox.showerror("Error", str(exc))
            return

        self.class_code.set("")
        self.class_title.set("")
        self.refresh_classes()
        messagebox.showinfo("Success", "Class registered successfully.")

    def register_assessment(self):
        try:
            self.controller.register_assessment(
                self.assessment_class.get(),
                self.assessment_type.get(),
                float(self.assessment_value.get()),
                float(self.assessment_weight.get()),
            )
        except Exception as exc:
            messagebox.showerror("Error", str(exc))
            return

        self.assessment_value.set("")
        self.assessment_weight.set("")
        self.refresh_classes()
        messagebox.showinfo("Success", "Assessment registered successfully.")

    def configure_persistence(self):
        try:
            self.controller.configure_persistence(self.persistence_type.get())
        except AcademicSystemException as exc:
            messagebox.showerror("Error", str(exc))
            return

        messagebox.showinfo("Success", "Persistence configured.")

    def save_data(self):
        try:
            self.controller.save()
        except Exception as exc:
            messagebox.showerror("Error", str(exc))
            return

        messagebox.showinfo("Success", "Academic data saved.")

    def refresh_classes(self):
        classes = self.controller.get_classes()
        codes = [academic_class.code for academic_class in classes]

        self.class_combo.configure(values=codes)
        if codes and not self.assessment_class.get():
            self.assessment_class.set(codes[0])

        for item in self.class_table.get_children():
            self.class_table.delete(item)

        for academic_class in classes:
            self.class_table.insert(
                "",
                tk.END,
                values=(
                    academic_class.code,
                    academic_class.title,
                    len(academic_class.assessments),
                ),
            )

        self.show_report(self.controller.generate_class_summary_report())

    def show_report(self, report: str):
        self.report_text.configure(state=tk.NORMAL)
        self.report_text.delete("1.0", tk.END)
        self.report_text.insert(tk.END, report)
        self.report_text.configure(state=tk.DISABLED)

    def logout(self):
        Session.logout()
        self.username.set("")
        self.password.set("")
        self.show_login()


def launch_app():
    AcademicSystemTkApp().run()