# -*- coding: utf-8 -*-
# Generated by Django 1.11 on 2018-01-29 03:19
from __future__ import unicode_literals

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('locations', '0001_initial'),
    ]

    operations = [
        migrations.AlterField(
            model_name='location',
            name='device_identification',
            field=models.TextField(),
        ),
    ]
