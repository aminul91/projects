#include "windowseconddialog.h"
#include "ui_windowseconddialog.h"

windowSecondDialog::windowSecondDialog(QWidget *parent) :
    QDialog(parent),
    ui(new Ui::windowSecondDialog)
{
    ui->setupUi(this);
}

windowSecondDialog::~windowSecondDialog()
{
    delete ui;
}
