#include "secondwindowdialog.h"
#include "ui_secondwindowdialog.h"
#include <QMessageBox>

secondWindowDialog::secondWindowDialog(QWidget *parent) :
    QDialog(parent),
    ui(new Ui::secondWindowDialog)
{
    ui->setupUi(this);
    ui->comboBox->addItem(QIcon(":/resource/img/A.png"),"DeviceA");
    ui->comboBox->addItem(QIcon(":/resource/img/B.png"),"DeviceB");
    ui->comboBox->addItem(QIcon(":/resource/img/C.png"),"DeviceC");
}

secondWindowDialog::~secondWindowDialog()
{
    delete ui;
}

void secondWindowDialog::on_pushButton_clicked()
{
    if(ui->checkBoxFirst ->isChecked())
    {
        QMessageBox::information(this,"Title chaeck","Click the chekbox");
    }
    else
        {
            QMessageBox::information(this,"Title chaeck","No Click the chekbox");
        }
}

void secondWindowDialog::on_pushButton_2_clicked()
{
    if(ui->radio1 ->isChecked())
    {
        QMessageBox::information(this,"Title radio 1","You are Male ");
    }
    else
        {
            QMessageBox::information(this,"Title radio 1","You are Female");
        }
}

void secondWindowDialog::on_pushButton_3_clicked()
{
     QMessageBox::information(this,"Title Device",ui->comboBox->currentText()+" start");
}


void secondWindowDialog::on_fileWindow_clicked()
{
    secondWindowDialog *secondDialogue = new secondWindowDialog(this);
    secondDialogue->show();

}

void secondWindowDialog::on_pushButton_4_clicked()

{
    thirdWindow *thirdWindowDialogue = new thirdWindow(this);
    thirdWindowDialogue->show();


}
