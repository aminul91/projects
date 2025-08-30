#include "thirdwindow.h"
#include "ui_thirdwindow.h"
#include <QFileDialog>
#include <QMessageBox>
#include <QDir>

thirdWindow::thirdWindow(QWidget *parent) :
    QDialog(parent),
    ui(new Ui::thirdWindow)
{
    ui->setupUi(this);
}

thirdWindow::~thirdWindow()
{
    delete ui;
}

void thirdWindow::on_pushButton_clicked()
{
    QString file_name = QFileDialog::getOpenFileName(this, "Open a File", QDir::homePath());// or write here the path 3rd position
}
